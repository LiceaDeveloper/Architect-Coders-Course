package com.liceadev.architectcoders.model.mappers


import com.liceadev.domain.User as DomainUser
import com.liceadev.architectcoders.model.database.User as RoomUser

fun RoomUser.toDomainUser(): DomainUser = DomainUser(
    userId,
    totalPhotos,
    totalLikes,
    portfolioUrl,
    profileImage,
    name,
    location,
    username
)

fun DomainUser.toRoomUser(): RoomUser = RoomUser(
    userId,
    totalPhotos,
    totalLikes,
    portfolioUrl,
    profileImage,
    name,
    location,
    username
)