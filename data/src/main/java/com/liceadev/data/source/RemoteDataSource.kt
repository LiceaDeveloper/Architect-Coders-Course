package com.liceadev.data.source

import com.liceadev.domain.Photo

interface RemoteDataSource {
    suspend fun getPhotos(apiKey: String, country: String): List<Photo>
}