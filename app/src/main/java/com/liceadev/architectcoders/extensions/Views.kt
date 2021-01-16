package com.liceadev.architectcoders.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide

fun ImageView.loadPhoto(url: String) {
    Glide
        .with(this.context)
        .load(url)
        .centerCrop()
        .into(this)
}