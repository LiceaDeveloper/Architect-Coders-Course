package com.liceadev.architectcoders

import com.liceadev.data.PermissionChecker
import com.liceadev.data.source.LocalDataSource
import com.liceadev.data.source.LocationDataSource
import com.liceadev.data.source.RemoteDataSource
import com.liceadev.domain.Photo
import com.liceadev.testshared.mockedPhoto
import kotlinx.coroutines.Dispatchers
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun initMockedDi(vararg modules: Module) {
    startKoin {
        modules(listOf(mockedAppModule, dataModule) + modules)
    }
}

private val mockedAppModule = module {
    single(named("apiKey")) { "12456" }
    single<LocalDataSource> { FakeLocalDataSource() }
    single<RemoteDataSource> { FakeRemoteDataSource() }
    single<LocationDataSource> { FakeLocationDataSource() }
    single<PermissionChecker> { FakePermissionChecker() }
    single { Dispatchers.Unconfined }
}
const val DEFAULT_COUNTRY = "Mexico"
const val ESPAÑA = "España"
const val COLOMBIA = "Colombia"

val defaultFakePhotos = listOf(
    mockedPhoto.copy(1),
    mockedPhoto.copy(2),
    mockedPhoto.copy(3),
    mockedPhoto.copy(4)
)

val españaFakePhotos = listOf(
    mockedPhoto.copy(5),
    mockedPhoto.copy(6),
    mockedPhoto.copy(7),
    mockedPhoto.copy(8)
)

val colombiaFakePhotos = listOf(
    mockedPhoto.copy(9),
    mockedPhoto.copy(10),
    mockedPhoto.copy(11),
    mockedPhoto.copy(12),
)

class FakeLocalDataSource : LocalDataSource {

    var photos: List<Photo> = emptyList()

    override suspend fun isEmpty() = photos.isEmpty()

    override suspend fun savePhotos(photos: List<Photo>) {
        this.photos = photos
    }

    override suspend fun getPhotos(): List<Photo> = photos

    override suspend fun findById(id: Int): Photo = photos.first { it.id == id }

    override suspend fun update(photo: Photo) {
        photos = photos.filterNot { it.id == photo.id } + photo
    }
}

class FakeRemoteDataSource : RemoteDataSource {

    var photos = defaultFakePhotos

    override suspend fun getPhotos(apiKey: String, country: String) =
        when (country) {
            ESPAÑA -> españaFakePhotos
            COLOMBIA -> colombiaFakePhotos
            else -> photos
        }

}

class FakeLocationDataSource : LocationDataSource {
    var country = DEFAULT_COUNTRY

    override suspend fun getCountry(): String = country
}

class FakePermissionChecker : PermissionChecker {
    var permissionGranted = true

    override suspend fun check(permission: PermissionChecker.Permission): Boolean =
        permissionGranted
}