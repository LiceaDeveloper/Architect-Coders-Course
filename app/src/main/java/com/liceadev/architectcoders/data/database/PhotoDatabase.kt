package com.liceadev.architectcoders.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Photo::class], version = 1)
abstract class PhotoDatabase : RoomDatabase() {

    companion object{
        fun build(context: Context)= Room.databaseBuilder(
            context,
            PhotoDatabase::class.java,
            "photo-db"
        ).build()
    }

    abstract fun photoDao(): PhotoDao
}