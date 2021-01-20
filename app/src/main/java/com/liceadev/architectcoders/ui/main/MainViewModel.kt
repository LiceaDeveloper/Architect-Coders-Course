package com.liceadev.architectcoders.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.liceadev.architectcoders.model.database.Photo
import com.liceadev.architectcoders.model.server.PhotosRepository
import com.liceadev.architectcoders.ui.common.Event
import com.liceadev.architectcoders.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class MainViewModel(private val photosRepository: PhotosRepository) : ScopedViewModel() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<Int>>()
    val navigation: LiveData<Event<Int>>
        get() {
            if (_navigation.value == null) refresh()
            return _navigation
        }

    sealed class UiModel {
        object Loading : UiModel()
        class Content(val photos: List<Photo>) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onPermissionRequested(){
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(photosRepository.findPopularPhotosByRegion())
        }
    }

    fun onPhotoClick(photo: Photo) {
        _navigation.value = Event(photo.id)
    }
}