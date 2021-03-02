package com.liceadev.architectcoders.data.database

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
    val likes: Int,
    val urlFull: String,
    val urlThumb: String,
    @Embedded val user: User,
    val favorite: Boolean
)

data class User(

    val userId: String?,

    val totalPhotos: Int?,

    val totalLikes: Int?,

    val portfolioUrl: String?,

    val profileImage: String?,

    val name: String?,

    val location: String?,

    val username: String?
)