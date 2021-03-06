package com.liceadev.architectcoders.data.server

import retrofit2.http.GET
import retrofit2.http.Query


interface PhotoServices {
    @GET("search/photos")
    suspend fun searchPhotos(
        @Query("client_id") apiKey: String,
        @Query("query") query: String,
        @Query("per_page") perPage: Int
    ): PhotosResponse
}