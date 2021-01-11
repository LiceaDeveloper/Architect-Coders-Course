package com.liceadev.mymovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.liceadev.mymovies.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mMoviesAdapter = MoviesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

         mMoviesAdapter.movies = listOf("1")

        with(binding) {
            rvMovies.adapter = mMoviesAdapter
        }
    }
}