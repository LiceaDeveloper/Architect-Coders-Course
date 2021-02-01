package com.liceadev.architectcoders.data.database

import androidx.room.*

@Dao
interface PhotoDao {

    @Query("SELECT * FROM Photo")
    fun getAll(): List<Photo>

    @Query("SELECT * FROM Photo WHERE id = :id")
    fun findById(id: Int): Photo

    @Query("SELECT COUNT(id) FROM Photo")
    fun photoCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertPhotos(photos: List<Photo>)

    @Update
    fun updatePhoto(photo: Photo)
}