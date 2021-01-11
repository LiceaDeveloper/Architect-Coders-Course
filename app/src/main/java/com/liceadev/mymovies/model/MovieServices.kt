package com.liceadev.mymovies.model

import retrofit2.http.GET
import retrofit2.http.Query


interface MovieServices {
    @GET("discover/movie?sort_by=popularity.desc")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String,
        @Query("region") region: String
    ): MoviesResponse
}