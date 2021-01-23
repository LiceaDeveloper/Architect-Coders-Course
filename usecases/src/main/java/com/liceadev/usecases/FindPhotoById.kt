package com.liceadev.usecases

import com.liceadev.data.PhotosRepository
import com.liceadev.domain.Photo

class FindPhotoById(private val photosRepository: PhotosRepository) {
    suspend fun invoke(id: Int): Photo = photosRepository.findById(id)
}