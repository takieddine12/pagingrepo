package com.example.moviesapppaginglibrary3.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.moviesapppaginglibrary3.db.TypeConverter
import org.jetbrains.annotations.NotNull

@Entity(tableName = "movieTable")
data class Result(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    val movieID : Long ,
    val id: Int,
    val adult: Boolean,
    val backdrop_path: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)