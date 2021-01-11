package com.liceadev.mymovies

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.liceadev.mymovies.databinding.ActivityMainBinding
import com.liceadev.mymovies.model.Movie
import com.liceadev.mymovies.model.MovieClient
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var mMoviesAdapter: MoviesAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            requestPopularMovies(isGranted)
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        mMoviesAdapter = MoviesAdapter { movie ->
            navigateTo(movie)
        }

        with(binding) {
            rvMovies.adapter = mMoviesAdapter
        }


        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
    }

    private fun navigateTo(movie: Movie) {
        startActivity(DetailActivity.getIntent(this, movie))
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    @SuppressLint("MissingPermission")
    private fun requestPopularMovies(isLocationGranted: Boolean) {
        if (isLocationGranted) {
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                doRequestPopularMovies(getRegionFromLocation(it.result))
            }
        }
    }

    private fun doRequestPopularMovies(regionFromLocation: String) {
        lifecycleScope.launch {
            val movies = MovieClient.service
                .getPopularMovies("ac3bca4dbf2d39b2f3ea35e968f18234", regionFromLocation)
            mMoviesAdapter.movies = movies.results
        }
    }

    private fun getRegionFromLocation(location: Location): String {
        val geocoder = Geocoder(this)
        val result = geocoder.getFromLocation(location.latitude, location.longitude, 1)
        return result.firstOrNull()?.countryCode ?: "US"
    }
}