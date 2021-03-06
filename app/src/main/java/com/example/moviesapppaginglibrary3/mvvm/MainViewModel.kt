package com.example.moviesapppaginglibrary3.mvvm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.moviesapppaginglibrary3.auth.AuthResponse
import com.example.moviesapppaginglibrary3.db.MovieDatabase
import com.example.moviesapppaginglibrary3.db.MoviesDao
import com.example.moviesapppaginglibrary3.db.MoviesMediator
import com.example.moviesapppaginglibrary3.models.MoviesDataModel
import com.example.moviesapppaginglibrary3.models.Result
import com.example.moviesapppaginglibrary3.paging.MoviePagingSource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.concurrent.Flow
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private  var authResponse: AuthResponse,
    private var movieDatabase: MovieDatabase
) : ViewModel() {


    val dataFlow : kotlinx.coroutines.flow.Flow<PagingData<Result>> =
        Pager(getPagingConfig(),
        remoteMediator = MoviesMediator(authResponse,movieDatabase)){
            MoviePagingSource(authResponse)
        }.flow
            .cachedIn(viewModelScope)


    // get config
    private fun getPagingConfig() : PagingConfig {
        return PagingConfig(pageSize = 10)
    }



    suspend fun offlineDataFlow() : kotlinx.coroutines.flow.Flow<PagingData<Result>> {
        return  Pager(getPagingConfig()){
            movieDatabase.MovieDao().getMovies()
        }.flow
                .cachedIn(viewModelScope)
    }

}