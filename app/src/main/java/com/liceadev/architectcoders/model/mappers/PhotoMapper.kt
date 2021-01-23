package com.liceadev.architectcoders.model.mappers

import com.liceadev.architectcoders.model.database.Photo  as RoomPhoto
import com.liceadev.domain.Photo as DomainPhoto
import com.liceadev.domain.User as DomainUser
import com.liceadev.architectcoders.model.server.Photo as ServerPhoto

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

    return com.liceadev.domain.Photo(
        0,
        photoDescription,
        photoLikes,
        photoUrlFull,
        photoUrlThumb,
        photoUser,
        false
    )
}

fun DomainPhoto.toRoomPhoto(): RoomPhoto =
    RoomPhoto(
        id = id,
        description = description,
        likes = likes,
        urlFull = urlFull,
        urlThumb = urlThumb,
        user = user.toRoomUser(),
        favorite = favorite
    )

fun RoomPhoto.toDomainPhoto(): DomainPhoto = DomainPhoto(
    id,
    description,
    likes,
    urlFull,
    urlThumb,
    user.toDomainUser(),
    favorite
)