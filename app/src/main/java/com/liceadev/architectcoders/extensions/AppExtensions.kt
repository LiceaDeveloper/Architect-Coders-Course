package com.liceadev.architectcoders.extensions

import android.content.Context
import com.liceadev.architectcoders.PhotoApp

val Context.app: PhotoApp
    get() = applicationContext as PhotoApp