package com.liceadev.architectcoders.di

import android.app.Application
import com.liceadev.architectcoders.ui.detail.DetailFragmentComponent
import com.liceadev.architectcoders.ui.detail.DetailFragmentModule
import com.liceadev.architectcoders.ui.detail.DetailViewModel
import com.liceadev.architectcoders.ui.main.MainFragmentComponent
import com.liceadev.architectcoders.ui.main.MainFragmentModule
import com.liceadev.architectcoders.ui.main.MainViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface MyPhotoComponent {

    fun plus(module: MainFragmentModule): MainFragmentComponent
    fun plus(module: DetailFragmentModule): DetailFragmentComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): MyPhotoComponent
    }
}