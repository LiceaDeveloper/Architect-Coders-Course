package com.liceadev.architectcoders.data.server

import com.liceadev.architectcoders.data.mappers.toDomainPhoto
import com.liceadev.data.source.RemoteDataSource
import com.liceadev.domain.Photo as DomainPhoto

class UnsplashDataSource(private val  photoClient:PhotoClient) : RemoteDataSource {

    override suspend fun getPhotos(apiKey: String, country: String): List<DomainPhoto> =
        photoClient
            .service
            .searchPhotos(apiKey, country, 30)
            .results
            .map { it.toDomainPhoto() }
}

