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

    suspend fun findById(id: Int): Photo = localDataSource.findById(id)

    suspend fun update(photo: Photo) = localDataSource.update(photo)
}

interface LocalDataSource {
    suspend fun isEmpty(): Boolean
    suspend fun savePhotos(photos: List<Photo> )
    suspend fun getPhotos(): List<Photo>
    suspend fun findById(id: Int): Photo
    suspend fun update(photo: Photo)
}

interface RemoteDataSource {
     suspend fun getPhotos(apiKey: String, country: String): List<Photo>
}