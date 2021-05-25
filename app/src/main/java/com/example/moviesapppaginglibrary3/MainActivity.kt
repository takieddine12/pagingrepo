package com.example.moviesapppaginglibrary3


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesapppaginglibrary3.adapters.LoadingStateAdapter
import com.example.moviesapppaginglibrary3.adapters.MoviesAdapter
import com.example.moviesapppaginglibrary3.databinding.ActivityMainBinding
import com.example.moviesapppaginglibrary3.mvvm.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest


@ExperimentalPagingApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var moviesAdapter: MoviesAdapter
    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!
    private  val moviesModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.layoutManager = GridLayoutManager(this,2)
        binding.recycler.setHasFixedSize(true)

        // set it up


        if(Constants.isConnected(this)){
            setUpAdapterOnline()
        } else {
            setUpAdapterOffline()
        }
    }

    private fun setUpAdapterOffline() {
        moviesAdapter = MoviesAdapter()
        lifecycleScope.launchWhenStarted {
            moviesModel.offlineDataFlow().collectLatest {
                moviesAdapter.submitData(it)
            }
        }

        binding.recycler.adapter = moviesAdapter
        binding.recycler.adapter =  moviesAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter { moviesAdapter.retry() },
                footer = LoadingStateAdapter { moviesAdapter.retry() }
        )
    }
    @ExperimentalPagingApi
    private fun setUpAdapterOnline(){
        moviesAdapter = MoviesAdapter()
        lifecycleScope.launchWhenStarted {
            moviesModel.dataFlow.collectLatest {
                moviesAdapter.submitData(it)
            }
        }

        binding.recycler.adapter = moviesAdapter
        binding.recycler.adapter =  moviesAdapter.withLoadStateHeaderAndFooter(
                header = LoadingStateAdapter { moviesAdapter.retry() },
                footer = LoadingStateAdapter { moviesAdapter.retry() }
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}