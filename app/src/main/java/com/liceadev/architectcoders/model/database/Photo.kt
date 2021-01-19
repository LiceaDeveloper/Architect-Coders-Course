package com.liceadev.architectcoders.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
    val likes: Int,
    val urlFull: String,
    val urlThumb: String,
//    val user: User,
    val favorite: Boolean
)