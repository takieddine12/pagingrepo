package com.example.moviesapppaginglibrary3

import android.content.Context
import android.net.ConnectivityManager

object Constants {

    const val MOVIES_API_URL = "https://api.themoviedb.org/3/movie/"
    const val API_KEY  = "bbfda310b517daeeec61e3493ab5efd8"
    const val LANGUAGE = "en_US"

    fun isConnected(context: Context) : Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return (networkInfo != null && networkInfo.isConnectedOrConnecting)
    }
 }