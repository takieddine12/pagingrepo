package com.example.moviesapppaginglibrary3.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.moviesapppaginglibrary3.R
import com.example.moviesapppaginglibrary3.databinding.LoadingRowsLayoutBinding

class LoadingStateAdapter(
    private val retry: () -> Unit
) : LoadStateAdapter<LoadingStateAdapter.PassengerLoadStateViewHolder>() {

    inner class PassengerLoadStateViewHolder(
        private val binding: LoadingRowsLayoutBinding,
        private val retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                binding.textViewError.text = loadState.error.localizedMessage
            }
            binding.progressbar.isVisible = loadState is LoadState.Loading
            binding.buttonRetry.isVisible = loadState is LoadState.Error
            binding.textViewError.isVisible = loadState is LoadState.Error
            binding.buttonRetry.setOnClickListener {
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: PassengerLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) = PassengerLoadStateViewHolder(
        LoadingRowsLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false),
        retry
    )
}