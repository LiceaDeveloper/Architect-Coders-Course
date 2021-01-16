package com.liceadev.architectcoders.ui.detail

import com.liceadev.architectcoders.model.Photo

class DetailPresenter {
    interface View {
        fun showPhoto(photo: Photo)
    }

    private var mView: View? = null

    fun onCreate(view: View, photo: Photo) {
        mView = view
        mView?.showPhoto(photo)
    }

    fun onDestroy() {
        mView = null
    }
}