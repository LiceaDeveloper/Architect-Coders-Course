package com.liceadev.data

import com.liceadev.data.source.LocalDataSource
import com.liceadev.data.source.RemoteDataSource
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



