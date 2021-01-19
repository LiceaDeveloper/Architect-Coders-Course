package com.liceadev.architectcoders.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.liceadev.architectcoders.model.server.Photo

class DetailViewModel(private val photo: Photo) : ViewModel() {

    class UiModel(val photo: Photo)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) _model.value = UiModel(photo)
            return _model
        }
}