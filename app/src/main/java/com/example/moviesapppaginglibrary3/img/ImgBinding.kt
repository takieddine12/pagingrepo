package com.example.moviesapppaginglibrary3.img

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.moviesapppaginglibrary3.R
import com.squareup.picasso.Picasso

object ImgBinding  {

    @BindingAdapter("img")
    @JvmStatic
    fun getMovieImg(view : ImageView , url : String?){
        Picasso.get().load("http://image.tmdb.org/t/p/w500/$url").into(view)
    }
}