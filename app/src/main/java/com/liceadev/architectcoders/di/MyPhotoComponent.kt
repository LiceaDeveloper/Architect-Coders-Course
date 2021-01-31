package com.liceadev.architectcoders.di

import android.app.Application
import com.liceadev.architectcoders.ui.detail.DetailViewModel
import com.liceadev.architectcoders.ui.main.MainViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class, UseCaseModule::class, ViewModelsModule::class])
interface MyPhotoComponent {

    val mainViewModel: MainViewModel
    val detailViewModel: DetailViewModel

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyPhotoComponent
    }
}