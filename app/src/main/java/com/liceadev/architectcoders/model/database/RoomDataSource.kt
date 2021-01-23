package com.liceadev.architectcoders.model.database

import com.liceadev.architectcoders.model.mappers.toDomainPhoto
import com.liceadev.architectcoders.model.mappers.toRoomPhoto
import com.liceadev.data.source.LocalDataSource
import com.liceadev.domain.Photo as DomainPhoto
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



