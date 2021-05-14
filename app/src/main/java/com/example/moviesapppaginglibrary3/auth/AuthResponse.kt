package com.example.moviesapppaginglibrary3.auth

import com.example.moviesapppaginglibrary3.models.MoviesDataModel
import retrofit2.http.GET
import retrofit2.http.Query

interface AuthResponse {
    @GET("now_playing?")
    suspend fun getMovies(
        @Query("api_key") apiKey : String ,
        @Query("language") language : String ,
        @Query("page") page : Int
    ) : MoviesDataModel
}