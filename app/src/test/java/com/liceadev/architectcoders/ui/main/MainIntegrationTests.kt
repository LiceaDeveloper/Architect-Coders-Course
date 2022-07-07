package com.liceadev.architectcoders.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.liceadev.architectcoders.*
import com.liceadev.data.PermissionChecker
import com.liceadev.data.source.LocalDataSource
import com.liceadev.data.source.LocationDataSource
import com.liceadev.testshared.mockedPhoto
import com.liceadev.usecases.GetPhotos
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

    lateinit var viewModel: MainViewModel


    @Before
    fun setUp() {
        val viewModelModule = module {
            factory { MainViewModel(get(), get()) }
            factory { GetPhotos(get()) }
        }

        initMockedDi(viewModelModule)
        viewModel = get()
    }

    @Test
    fun `get Photos from server when local is empty`() {
        viewModel.model.observeForever(observer)

        viewModel.onPermissionRequested()

        verify(observer).onChanged(MainViewModel.UiModel.Content(defaultFakePhotos))
    }

    @Test
    fun `data is loaded from local source when available`() {
        val fakeLocalPhotos = listOf(mockedPhoto.copy(10), mockedPhoto.copy(11))
        val localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.photos = fakeLocalPhotos
        viewModel.model.observeForever(observer)

        viewModel.onPermissionRequested()

        verify(observer).onChanged(MainViewModel.UiModel.Content(fakeLocalPhotos))
    }

    @Test
    fun `from server when Permission Denied `() {
        //GIVEN
        viewModel.model.observeForever(observer)
        val permissionChecker = get<PermissionChecker>() as FakePermissionChecker
        permissionChecker.permissionGranted = false
        //WHEN
        viewModel.onPermissionRequested()

        //THEN
        verify(observer).onChanged(MainViewModel.UiModel.Content(defaultFakePhotos))
    }


    @Test
    fun `from server when country is colombia `() {
        //GIVEN
        viewModel.model.observeForever(observer)
        val locationDataSource = get<LocationDataSource>() as FakeLocationDataSource
        locationDataSource.country = COLOMBIA

        //WHEN
        viewModel.onPermissionRequested()

        //THEN
        verify(observer).onChanged(MainViewModel.UiModel.Content(colombiaFakePhotos))
    }

    @Test
    fun `from server when country is españa `() {
        //GIVEN
        viewModel.model.observeForever(observer)
        val locationDataSource = get<LocationDataSource>() as FakeLocationDataSource
        locationDataSource.country = ESPAÑA

        //WHEN
        viewModel.onPermissionRequested()

        //THEN
        verify(observer).onChanged(MainViewModel.UiModel.Content(españaFakePhotos))
    }
}