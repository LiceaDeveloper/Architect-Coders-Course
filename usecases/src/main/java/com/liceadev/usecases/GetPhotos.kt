package com.liceadev.usecases

import com.liceadev.data.PhotosRepository
import com.liceadev.domain.Photo

class GetPhotos(private val photosRepository: PhotosRepository) {
    suspend fun invoke(): List<Photo> = photosRepository.getPhotos()
}