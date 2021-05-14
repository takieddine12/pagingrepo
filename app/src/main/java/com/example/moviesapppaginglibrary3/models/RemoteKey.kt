package com.example.moviesapppaginglibrary3.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    @NotNull
    val movieId: Long,
    val prevKey: Int?,
    val nextKey: Int?
)