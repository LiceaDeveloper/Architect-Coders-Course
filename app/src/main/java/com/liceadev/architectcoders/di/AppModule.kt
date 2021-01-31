package com.liceadev.architectcoders.di

import android.app.Application
import androidx.room.Room
import com.liceadev.architectcoders.R
import com.liceadev.architectcoders.model.AndroidPermissionChecker
import com.liceadev.architectcoders.model.PlayServicesLocation
import com.liceadev.architectcoders.model.database.PhotoDatabase
import com.liceadev.architectcoders.model.database.RoomDataSource
import com.liceadev.architectcoders.model.server.UnsplashDataSource
import com.liceadev.data.PermissionChecker
import com.liceadev.data.source.LocalDataSource
import com.liceadev.data.source.LocationDataSource
import com.liceadev.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.unsplash_api_key)

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        PhotoDatabase::class.java,
        "photo-db"
    ).build()

    @Provides
    fun localDataSourceProvider(db: PhotoDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = UnsplashDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocation(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}