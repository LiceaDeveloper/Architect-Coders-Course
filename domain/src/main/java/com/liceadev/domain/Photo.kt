package com.liceadev.domain

data class Photo(
    val id: Int,
    val description: String,
    val likes: Int,
    val urlFull: String,
    val urlThumb: String,
    val user: User,
    val favorite: Boolean
)