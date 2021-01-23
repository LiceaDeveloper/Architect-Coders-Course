package com.liceadev.data

import com.liceadev.domain.Photo

class PhotosRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val countryRepository: CountryRepository,
    private val apiKey: String
) {

    suspend fun getPhotos(): List<Photo> {
        if (localDataSource.isEmpty()) {
            val photos = remoteDataSource.getPhotos(apiKey, countryRepository.findCountry())
            localDataSource.savePhotos(photos)
        }
        return localDataSource.getPhotos()
    }
}

interface LocalDataSource {
    fun isEmpty(): Boolean
    fun savePhotos(photos: List<Photo> )
    fun getPhotos(): List<Photo>
}

interface RemoteDataSource {
     fun getPhotos(apiKey: String, country: String): List<Photo>
}