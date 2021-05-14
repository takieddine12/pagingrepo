package com.example.moviesapppaginglibrary3.models

import androidx.room.TypeConverters
import com.example.moviesapppaginglibrary3.db.TypeConverter


data class MoviesDataModel(
    val dates: Dates,
    val page: Int,
    val results: List<Result>,
    val total_pages: Int,
    val total_results: Int
)