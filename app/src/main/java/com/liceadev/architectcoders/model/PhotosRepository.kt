package com.liceadev.architectcoders.model

import android.app.Activity
import com.liceadev.architectcoders.R

class PhotosRepository(activity: Activity) {
    private val apiKey = activity.getString(R.string.unsplash_api_key)
    private val regionRepository = CountryRepository(activity)

    suspend fun findPopularPhotosByRegion(): PhotosResponse {
        val country = regionRepository.findLastCountry()
        return PhotoClient
            .SERVICE
            .getPopularPhotos(apiKey, country, 30)
    }
}