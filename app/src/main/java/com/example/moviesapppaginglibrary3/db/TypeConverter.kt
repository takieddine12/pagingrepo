package com.example.moviesapppaginglibrary3.db
import com.example.moviesapppaginglibrary3.models.Result
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class TypeConverter {

    @androidx.room.TypeConverter
    fun fromString(value: String?): List<Result>? {
        val listType = object : TypeToken<List<Result>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @androidx.room.TypeConverter
    fun fromArrayList(list: List<Result>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}