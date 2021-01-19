package com.liceadev.architectcoders.model.server

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhotoClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val service: PhotoServices = retrofit.create(PhotoServices::class.java)
}