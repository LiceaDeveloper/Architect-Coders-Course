package com.liceadev.architectcoders

import android.app.Application

class PhotoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}