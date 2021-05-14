package com.example.moviesapppaginglibrary3.db

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moviesapppaginglibrary3.models.RemoteKeys
import com.example.moviesapppaginglibrary3.models.Result

@Database(entities = [Result::class,RemoteKeys::class],version = 1,exportSchema = false)
abstract class MovieDatabase : RoomDatabase(){

    abstract fun MovieDao() : MoviesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}