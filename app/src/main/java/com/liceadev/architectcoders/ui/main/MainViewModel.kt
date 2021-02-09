package com.liceadev.architectcoders.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.liceadev.architectcoders.ui.common.Event
import com.liceadev.architectcoders.ui.common.ScopedViewModel
import com.liceadev.domain.Photo
import com.liceadev.usecases.GetPhotos
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(
    private val getPhotos: GetPhotos,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

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
        data class Content(val photos: List<Photo>) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onPermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getPhotos.invoke())
        }
    }

    fun onPhotoClick(photo: Photo) {
        _navigation.value = Event(photo.id)
    }
}