package com.liceadev.mymovies.model

import android.app.Activity
import com.liceadev.mymovies.R

class MoviesRepository(activity: Activity) {
    private val apiKey = activity.getString(R.string.api_key)
    private val regionRepository = RegionRepository(activity)

    suspend fun findPopularMoviesByRegion() = MovieClient
        .service
        .getPopularMovies(apiKey,regionRepository.findLastRegion())
}