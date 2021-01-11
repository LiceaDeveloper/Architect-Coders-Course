package com.liceadev.mymovies

import android.Manifest
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.liceadev.mymovies.databinding.ActivityMainBinding
import com.liceadev.mymovies.model.Movie
import com.liceadev.mymovies.model.MovieClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mMoviesAdapter: MoviesAdapter

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            val message = if (isGranted) {
                "Permission Granted"
            } else {
                "Permission Denied"
            }
            toast(message)
        }

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


        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)

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

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}