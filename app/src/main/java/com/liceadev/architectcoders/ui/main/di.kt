package com.liceadev.architectcoders.ui.main

import com.liceadev.data.PhotosRepository
import com.liceadev.usecases.GetPhotos
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainFragmentModule {

    @Provides
    fun mainViewModelProvider(getPhotos: GetPhotos) = MainViewModel(getPhotos)

    @Provides
    fun getPhotosProvider(photosRepository: PhotosRepository) = GetPhotos(photosRepository)
}

@Subcomponent(modules = [MainFragmentModule::class])
interface MainFragmentComponent {
    val mainViewModel: MainViewModel
}