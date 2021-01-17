package com.liceadev.architectcoders.model

import android.app.Application
import com.liceadev.architectcoders.R

class PhotosRepository(application: Application) {
    private val apiKey = application.getString(R.string.unsplash_api_key)
    private val regionRepository = CountryRepository(application)

    suspend fun findPopularPhotosByRegion(): PhotosResponse {
        val country = regionRepository.findLastCountry()
        return PhotoClient
            .SERVICE
            .getPopularPhotos(apiKey, country, 30)
    }
}