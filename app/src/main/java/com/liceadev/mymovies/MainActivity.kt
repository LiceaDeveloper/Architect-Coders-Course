package com.liceadev.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.liceadev.mymovies.databinding.ActivityMainBinding
import com.liceadev.mymovies.model.Movie
import com.liceadev.mymovies.model.MovieClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

   private lateinit var mMoviesAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMoviesAdapter = MoviesAdapter { movie ->
            navigateTo(movie)
        }

        with(binding) {
            rvMovies.adapter = mMoviesAdapter
        }



        loadMovies()
    }

    private fun navigateTo(movie: Movie) {
        startActivity(DetailActivity.getIntent(this, movie))
    }

    private fun loadMovies() {
        lifecycleScope.launch {
            val movies = MovieClient.service
                .getPopularMovies("ac3bca4dbf2d39b2f3ea35e968f18234")
            mMoviesAdapter.movies = movies.results
        }
    }
}