package com.example.moviesapppaginglibrary3.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moviesapppaginglibrary3.models.Result

@Dao
interface MoviesDao {

    @Query("SELECT * FROM movieTable ORDER BY id")
     fun getMovies() : PagingSource<Int,Result>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(result: List<Result>)


    @Query("DELETE FROM movieTable")
    suspend fun clearMovies()


}