package com.liceadev.usecases

import com.liceadev.data.PhotosRepository
import com.liceadev.domain.Photo

class TogglePhotoFavorite(private val photosRepository: PhotosRepository) {
    suspend fun invoke(photo: Photo): Photo = with(photo) {
        copy(favorite = !favorite).also { photosRepository.update(it) }
    }
}