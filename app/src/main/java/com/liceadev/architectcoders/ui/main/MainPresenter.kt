package com.liceadev.architectcoders.ui.main

import com.liceadev.architectcoders.model.PhotosRepository
import com.liceadev.architectcoders.model.Photo
import com.liceadev.architectcoders.model.Scope
import kotlinx.coroutines.launch

class MainPresenter(private val photosRepository: PhotosRepository) : Scope by Scope.ScopeImpl() {
    interface View {
        fun showProgress()
        fun loadPhotos(photos: List<Photo>)
        fun hideProgress()
        fun navigateToPhoto(photo: Photo)
    }

    private var mView: View? = null

    fun onCreate(view: View) {
        initScope()
        mView = view
        launch {
            mView?.apply {
                showProgress()
                loadPhotos(photosRepository.findPopularPhotosByRegion().results)
                hideProgress()
            }
        }
    }

    fun onDestroy() {
        cancelScope()
        mView = null
    }

    fun onPhotoClick(photo: Photo) {
        mView?.navigateToPhoto(photo)
    }
}