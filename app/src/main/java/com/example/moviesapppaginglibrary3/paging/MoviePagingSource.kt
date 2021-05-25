package com.example.moviesapppaginglibrary3.paging

import android.annotation.SuppressLint
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviesapppaginglibrary3.Constants
import com.example.moviesapppaginglibrary3.auth.AuthResponse
import com.example.moviesapppaginglibrary3.models.Result
import timber.log.Timber
import java.lang.Exception

class MoviePagingSource(
    var authResponse: AuthResponse
) : PagingSource<Int, Result>() {


    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, Result>): Int? {
        return 0
    }

    @SuppressLint("TimberArgCount")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Result> {
        return try {
            val nextPageKey = params.key ?: 1
            val response  = authResponse.getMovies(Constants.API_KEY,Constants.LANGUAGE,nextPageKey)

            LoadResult.Page(
                data = response.results,
                prevKey = if(nextPageKey == 1) null else nextPageKey - 1,
                nextKey = nextPageKey + 1
            )

        }catch (ex : Exception){
            LoadResult.Error(ex)
        }
    }


}