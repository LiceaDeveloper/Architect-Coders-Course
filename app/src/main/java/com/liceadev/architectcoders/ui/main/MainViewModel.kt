package com.liceadev.architectcoders.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liceadev.architectcoders.model.server.PhotosRepository
import com.liceadev.architectcoders.model.server.Photo
import com.liceadev.architectcoders.model.Scope
import kotlinx.coroutines.launch

class MainViewModel(private val photosRepository: PhotosRepository) : ViewModel(),
    Scope by Scope.ScopeImpl() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val photos: List<Photo>) : UiModel()
        class Navigation(val photo: Photo) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onPermissionRequested(){
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(photosRepository.findPopularPhotosByRegion().results)
        }
    }

    fun onPhotoClick(photo: Photo) {
        _model.value = UiModel.Navigation(photo)
    }


    override fun onCleared() {
        super.onCleared()
        cancelScope()
    }
}