package com.liceadev.architectcoders.data.mappers


import com.liceadev.domain.User as DomainUser
import com.liceadev.architectcoders.data.database.User as RoomUser

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