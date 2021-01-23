package com.liceadev.architectcoders.model.database

import com.liceadev.data.source.LocalDataSource
import com.liceadev.domain.Photo as DomainPhoto
import com.liceadev.domain.User as DomainUser
import com.liceadev.architectcoders.model.database.Photo as RoomPhoto
import com.liceadev.architectcoders.model.database.User as RoomUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class RoomDataSource(db: PhotoDatabase) : LocalDataSource {

    private val photoDao = db.photoDao()

    override suspend fun isEmpty(): Boolean =
        withContext(Dispatchers.IO) { photoDao.photoCount() <= 0 }

    override suspend fun savePhotos(photos: List<DomainPhoto>) {
        withContext(Dispatchers.IO) { photoDao.insertPhotos(photos.map { it.toRoomPhoto() }) }
    }

    override suspend fun getPhotos(): List<DomainPhoto> = withContext(Dispatchers.IO) {
        photoDao.getAll().map { it.toDomainPhoto() }
    }

    override suspend fun findById(id: Int): DomainPhoto = withContext(Dispatchers.IO) {
        photoDao.findById(id).toDomainPhoto()
    }

    override suspend fun update(photo: DomainPhoto) = withContext(Dispatchers.IO) {
        photoDao.updatePhoto(photo.toRoomPhoto())
    }
}

fun DomainPhoto.toRoomPhoto(): RoomPhoto = RoomPhoto(
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