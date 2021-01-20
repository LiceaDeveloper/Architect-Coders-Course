package com.liceadev.architectcoders.model.server

import com.liceadev.architectcoders.PhotoApp
import com.liceadev.architectcoders.R
import com.liceadev.architectcoders.model.CountryRepository
import com.liceadev.architectcoders.model.database.Photo as DbPhoto
import com.liceadev.architectcoders.model.server.Photo as ServerPhoto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PhotosRepository(application: PhotoApp) {

    private val apiKey = application.getString(R.string.unsplash_api_key)
    private val regionRepository = CountryRepository(application)
    private val db = application.db

    suspend fun findPopularPhotosByRegion(): List<DbPhoto> = withContext(Dispatchers.IO) {

        with(db.photoDao()) {
            if (photoCount() <= 0) {

                val movies = PhotoClient.service
                    .getPopularPhotos(apiKey, regionRepository.findLastCountry(),30)
                    .results

                insertPhotos(movies.map(ServerPhoto::convertToDbMovie))
            }

            getAll()
        }
    }

    suspend fun findById(id: Int): DbPhoto = withContext(Dispatchers.IO) {
        db.photoDao().findById(id)
    }

    suspend fun update(movie: DbPhoto) = withContext(Dispatchers.IO) {
        db.photoDao().updatePhoto(movie)
    }
}

private fun ServerPhoto.convertToDbMovie(): DbPhoto {
    val photoDescription = description ?: (altDescription ?: "")
    val photoUrlFull = urls?.full ?: (urls?.regular ?: "")
    val photoUrlThumb = urls?.thumb ?: ""
    val photoLikes = likes ?: 0

    return DbPhoto(
        0,
        photoDescription,
        photoLikes,
        photoUrlFull,
        photoUrlThumb,
        false
    )
}