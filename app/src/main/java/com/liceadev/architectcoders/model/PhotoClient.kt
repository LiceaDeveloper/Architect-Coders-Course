package com.liceadev.architectcoders.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PhotoClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val SERVICE: PhotoServices = retrofit.create(PhotoServices::class.java)
}