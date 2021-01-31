package com.liceadev.architectcoders.di

import com.liceadev.architectcoders.ui.detail.DetailViewModel
import com.liceadev.architectcoders.ui.main.MainViewModel
import com.liceadev.usecases.FindPhotoById
import com.liceadev.usecases.GetPhotos
import com.liceadev.usecases.TogglePhotoFavorite
import dagger.Module
import dagger.Provides

@Module
class ViewModelsModule {

    @Provides
    fun mainViewModelProvider(getPhotos: GetPhotos) = MainViewModel(getPhotos)

    @Provides
    fun detailViewModelProvider(
        findPhotoById: FindPhotoById,
        togglePhotoFavorite: TogglePhotoFavorite
    ): DetailViewModel {
        return DetailViewModel(-1, findPhotoById, togglePhotoFavorite)
    }
}