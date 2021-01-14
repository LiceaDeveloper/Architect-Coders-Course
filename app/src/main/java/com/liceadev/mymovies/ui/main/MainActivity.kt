package com.liceadev.mymovies.ui.main

import android.os.Bundle
import com.liceadev.mymovies.databinding.ActivityMainBinding
import com.liceadev.mymovies.model.MoviesRepository
import com.liceadev.mymovies.ui.common.CoroutineScopeActivity
import com.liceadev.mymovies.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class MainActivity : CoroutineScopeActivity() {

    private val moviesRepository: MoviesRepository by lazy { MoviesRepository(this) }

    private var mMoviesAdapter: MoviesAdapter = MoviesAdapter { movie ->
        startActivity(DetailActivity.getIntent(this, movie))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvMovies.adapter = mMoviesAdapter

        launch {
            mMoviesAdapter.movies = moviesRepository.findPopularMoviesByRegion().results
        }
    }
}