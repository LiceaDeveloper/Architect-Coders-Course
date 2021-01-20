package com.liceadev.architectcoders.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.liceadev.architectcoders.model.server.ProfileImage

@Entity
data class Photo(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val description: String,
    val likes: Int,
    val urlFull: String,
    val urlThumb: String,
    val user: User,
    val favorite: Boolean
)

@Entity
data class User(

    val id: String? = null,

    val totalPhotos: Int? = null,

    val totalLikes: Int? = null,

    val portfolioUrl: String? = null,

    val profileImage: ProfileImage? = null,

    val name: String? = null,

    val location: String? = null,

    val username: String? = null
)