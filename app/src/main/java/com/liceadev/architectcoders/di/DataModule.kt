package com.liceadev.architectcoders.di

import com.liceadev.data.CountryRepository
import com.liceadev.data.PermissionChecker
import com.liceadev.data.PhotosRepository
import com.liceadev.data.source.LocalDataSource
import com.liceadev.data.source.LocationDataSource
import com.liceadev.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun countryRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = CountryRepository(locationDataSource, permissionChecker)

    @Provides
    fun photosRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        countryRepository: CountryRepository,
        @Named("apiKey") apiKey: String
    ) = PhotosRepository(localDataSource, remoteDataSource, countryRepository, apiKey)
}