package com.liceadev.architectcoders

import android.app.Application
import androidx.room.Room
import com.liceadev.architectcoders.model.database.PhotoDatabase

class PhotoApp : Application() {

    lateinit var db: PhotoDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            PhotoDatabase::class.java, "photo-db"
        ).build()
    }
}