package com.liceadev.architectcoders.ui.detail

import com.liceadev.data.PhotosRepository
import com.liceadev.usecases.FindPhotoById
import com.liceadev.usecases.TogglePhotoFavorite
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailFragmentModule(private val photoId: Int) {
    @Provides
    fun detailViewModelProvider(
        findPhotoById: FindPhotoById,
        togglePhotoFavorite: TogglePhotoFavorite
    ): DetailViewModel {
        return DetailViewModel(photoId, findPhotoById, togglePhotoFavorite)
    }

    @Provides
    fun findPhotoByIdProvider(photosRepository: PhotosRepository) = FindPhotoById(photosRepository)

    @Provides
    fun togglePhotoFavoriteProvider(photosRepository: PhotosRepository) =
        TogglePhotoFavorite(photosRepository)
}

@Subcomponent(modules = [DetailFragmentModule::class])
interface DetailFragmentComponent {
    val detailViewModel: DetailViewModel
}