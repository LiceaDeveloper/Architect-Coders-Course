package com.liceadev.data.source

import com.liceadev.domain.Photo

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun savePhotos(photos: List<Photo> )
    suspend fun getPhotos(): List<Photo>
    suspend fun findById(id: Int): Photo
    suspend fun update(photo: Photo)
}