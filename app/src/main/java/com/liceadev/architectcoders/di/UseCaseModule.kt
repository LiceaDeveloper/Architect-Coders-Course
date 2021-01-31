package com.liceadev.architectcoders.di

import com.liceadev.data.PhotosRepository
import com.liceadev.usecases.FindPhotoById
import com.liceadev.usecases.GetPhotos
import com.liceadev.usecases.TogglePhotoFavorite
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @Provides
    fun findPhotoByIdProvider(photosRepository: PhotosRepository) = FindPhotoById(photosRepository)

    @Provides
    fun getPhotosProvider(photosRepository: PhotosRepository) = GetPhotos(photosRepository)

    @Provides
    fun togglePhotoFavoriteProvider(photosRepository: PhotosRepository) = TogglePhotoFavorite(photosRepository)
}