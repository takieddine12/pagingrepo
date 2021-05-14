package com.example.moviesapppaginglibrary3.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapppaginglibrary3.R
import com.example.moviesapppaginglibrary3.databinding.MovieRowsLayoutBinding
import com.example.moviesapppaginglibrary3.models.MoviesDataModel
import com.example.moviesapppaginglibrary3.models.Result

class MoviesAdapter  :
    PagingDataAdapter<Result,MoviesAdapter.MoviesViewHolder>(diffCall) {

    class MoviesViewHolder(var movieRowsLayoutBinding: MovieRowsLayoutBinding)
        : RecyclerView.ViewHolder(movieRowsLayoutBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view = DataBindingUtil.inflate<MovieRowsLayoutBinding>(
            LayoutInflater.from(parent.context),
            R.layout.movie_rows_layout,parent,false
        )
        return MoviesViewHolder(view)
    }
    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val data = getItem(position)
        holder.movieRowsLayoutBinding.model = data

        Log.d("TAG","Drop ${data?.backdrop_path}")
    }

    object diffCall : DiffUtil.ItemCallback<Result>(){
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
           return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }

    }




}