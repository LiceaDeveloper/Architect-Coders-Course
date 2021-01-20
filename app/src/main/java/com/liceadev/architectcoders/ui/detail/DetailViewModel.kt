package com.liceadev.architectcoders.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.liceadev.architectcoders.model.database.Photo
import com.liceadev.architectcoders.model.server.PhotosRepository
import com.liceadev.architectcoders.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class DetailViewModel(private val photoId: Int, private val photosRepository: PhotosRepository) :
    ScopedViewModel() {

    class UiModel(val photo: Photo)

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findPhoto()
            return _model
        }

    private fun findPhoto() = launch {
        _model.value = UiModel(photosRepository.findById(photoId))
    }

    fun onFavoriteClicked() = launch {
        _model.value?.photo?.let {
            val updatedPhoto = it.copy(favorite = !it.favorite)
            _model.value = UiModel(updatedPhoto)
            photosRepository.update(updatedPhoto)
        }
    }
}