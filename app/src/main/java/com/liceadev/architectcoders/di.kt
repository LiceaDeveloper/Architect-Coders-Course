package com.liceadev.architectcoders

import android.app.Application
import com.liceadev.architectcoders.data.AndroidPermissionChecker
import com.liceadev.architectcoders.data.PlayServicesLocation
import com.liceadev.architectcoders.data.database.PhotoDatabase
import com.liceadev.architectcoders.data.database.RoomDataSource
import com.liceadev.architectcoders.data.server.PhotoClient
import com.liceadev.architectcoders.data.server.UnsplashDataSource
import com.liceadev.architectcoders.ui.detail.DetailFragment
import com.liceadev.architectcoders.ui.detail.DetailViewModel
import com.liceadev.architectcoders.ui.main.MainFragment
import com.liceadev.architectcoders.ui.main.MainViewModel
import com.liceadev.data.CountryRepository
import com.liceadev.data.PermissionChecker
import com.liceadev.data.PhotosRepository
import com.liceadev.data.source.LocalDataSource
import com.liceadev.data.source.LocationDataSource
import com.liceadev.data.source.RemoteDataSource
import com.liceadev.usecases.FindPhotoById
import com.liceadev.usecases.GetPhotos
import com.liceadev.usecases.TogglePhotoFavorite
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.unsplash_api_key) }
    single { PhotoDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { UnsplashDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocation(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single(named("baseUrl")) { "https://api.unsplash.com/" }
    single { PhotoClient(get(named("baseUrl"))) }
}

val dataModule = module {
    factory { CountryRepository(get(), get()) }
    factory { PhotosRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MainFragment>()){
        viewModel { MainViewModel(get()) }
        scoped { GetPhotos(get()) }
    }

    scope(named<DetailFragment>()) {
        viewModel { (id: Int) -> DetailViewModel(id, get(), get()) }
        scoped { FindPhotoById(get()) }
        scoped { TogglePhotoFavorite(get()) }
    }
}