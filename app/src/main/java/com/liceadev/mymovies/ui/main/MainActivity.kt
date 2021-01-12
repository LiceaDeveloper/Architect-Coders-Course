package com.liceadev.mymovies.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.liceadev.mymovies.R
import com.liceadev.mymovies.databinding.ActivityMainBinding
import com.liceadev.mymovies.model.Movie
import com.liceadev.mymovies.model.MovieClient
import com.liceadev.mymovies.ui.common.CoroutineScopeActivity
import com.liceadev.mymovies.ui.detail.DetailActivity
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class MainActivity : CoroutineScopeActivity() {

    private lateinit var mMoviesAdapter: MoviesAdapter
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private val DEFAULT_REGION = "US"

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            doRequestPopularMovies(isGranted)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        mMoviesAdapter = MoviesAdapter { movie ->
            navigateTo(movie)
        }
        binding.rvMovies.adapter = mMoviesAdapter
    }

    private fun navigateTo(movie: Movie) {
        startActivity(DetailActivity.getIntent(this, movie))
    }

    private fun doRequestPopularMovies(isLocationGranted: Boolean) {
        launch {
            val region = getRegion(isLocationGranted)
            val movies = MovieClient.service
                .getPopularMovies(getString(R.string.api_key), region)
            mMoviesAdapter.movies = movies.results
        }
    }

    private fun getRegionFromLocation(location: Location?): String {
        val geocoder = Geocoder(this)
        val fromLocation = location?.let {
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        }
        return fromLocation?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }

    @SuppressLint("MissingPermission")
    private suspend fun getRegion(isLocationGranted: Boolean): String =
        suspendCancellableCoroutine { continuation ->
            if (isLocationGranted) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                    continuation.resume(getRegionFromLocation(it.result))
                }
            } else {
                continuation.resume(DEFAULT_REGION)
            }
        }
}