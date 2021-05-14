package com.example.moviesapppaginglibrary3.db

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.moviesapppaginglibrary3.Constants
import com.example.moviesapppaginglibrary3.auth.AuthResponse
import com.example.moviesapppaginglibrary3.models.RemoteKeys
import com.example.moviesapppaginglibrary3.models.Result
import java.lang.Exception

private const val MOVIES_API_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class MoviesMediator(
    private var authResponse: AuthResponse,
    private  var movieDatabase: MovieDatabase
) : RemoteMediator<Int,Result>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Result>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: MOVIES_API_STARTING_PAGE_INDEX
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val prevKey = remoteKeys?.prevKey
                    ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                prevKey
            }

        }

        try {
            val response = authResponse.getMovies(Constants.API_KEY, Constants.LANGUAGE, MOVIES_API_STARTING_PAGE_INDEX).results

            val endOfPagination = response.isEmpty()
            movieDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    movieDatabase.remoteKeysDao().clearRemoteKeys()
                    movieDatabase.MovieDao().clearMovies()
                }
                val prevKey = if (page == MOVIES_API_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1
                val keys = response.map {
                    RemoteKeys(movieId = it.id.toLong(), prevKey = prevKey, nextKey = nextKey)
                }
                movieDatabase.remoteKeysDao().insertAll(keys)
                movieDatabase.MovieDao().insertMovies(response)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (ex: Exception) {
            return MediatorResult.Error(ex)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, Result>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { movieId ->
                // Get the remote keys of the last item retrieved
                movieDatabase.remoteKeysDao().remoteKeysRepoId(movieId.id.toLong())
            }
    }
    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, Result>): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { movieId ->
                movieDatabase.remoteKeysDao().remoteKeysRepoId(movieId.toLong())
            }
        }
    }
}