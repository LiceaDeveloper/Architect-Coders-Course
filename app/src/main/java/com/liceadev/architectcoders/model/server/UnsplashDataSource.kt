package com.liceadev.architectcoders.model.server

import com.liceadev.data.source.RemoteDataSource
import com.liceadev.domain.Photo as DomainPhoto
import com.liceadev.domain.User as DomainUser
import com.liceadev.architectcoders.model.server.Photo as ServerPhoto

class UnsplashDataSource : RemoteDataSource {

    override suspend fun getPhotos(apiKey: String, country: String): List<DomainPhoto> =
        PhotoClient
            .service
            .searchPhotos(apiKey, country, 30)
            .results
            .map { it.toDomainPhoto() }

}

fun ServerPhoto.toDomainPhoto(): DomainPhoto {
    val photoDescription = description ?: (altDescription ?: "")
    val photoUrlFull = urls?.full ?: (urls?.regular ?: "")
    val photoUrlThumb = urls?.thumb ?: ""
    val photoLikes = likes ?: 0

    val photoUser = DomainUser(
        userId = user.id,
        totalPhotos = user.totalPhotos,
        totalLikes = user.totalLikes,
        portfolioUrl = user.portfolioUrl,
        profileImage = user.profileImage?.medium,
        name = user.name,
        location = user.location,
        username = user.username
    )

    return DomainPhoto(
        0,
        photoDescription,
        photoLikes,
        photoUrlFull,
        photoUrlThumb,
        photoUser,
        false
    )
}