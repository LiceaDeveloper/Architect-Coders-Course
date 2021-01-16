package com.liceadev.architectcoders.model

import com.google.gson.annotations.SerializedName

data class PhotosResponse(
    val results: List<Photo>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total") val totalResults: Int
)