package com.liceadev.architectcoders

import android.app.Application
import com.liceadev.architectcoders.di.DaggerMyPhotoComponent
import com.liceadev.architectcoders.di.MyPhotoComponent

class PhotoApp : Application() {

    lateinit var component: MyPhotoComponent
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerMyPhotoComponent
            .factory()
            .create(this)
    }
}