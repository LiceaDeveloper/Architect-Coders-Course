package com.liceadev.architectcoders.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.liceadev.domain.Photo
import com.liceadev.usecases.GetPhotos
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import com.liceadev.architectcoders.ui.main.MainViewModel.UiModel
import com.liceadev.testshared.mockedPhoto

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getPhotos: GetPhotos

    @Mock
    lateinit var observer: Observer<UiModel>

    private lateinit var viewModel: MainViewModel

    @Before
    fun setUp() {
        viewModel = MainViewModel(getPhotos, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData launches location permission request`() {
        viewModel.model.observeForever(observer)

        verify(observer).onChanged(UiModel.RequestLocationPermission)
    }

    @Test
    fun `show loading`() {
        runBlocking {

            val photos = listOf(mockedPhoto.copy(id = 1))
            whenever(getPhotos.invoke()).thenReturn(photos)
            viewModel.model.observeForever(observer)

            viewModel.onPermissionRequested()

            verify(observer).onChanged(UiModel.Loading)
        }
    }

    @Test
    fun `getPhotos called`() {
        runBlocking {
            val photos = listOf(mockedPhoto.copy(id = 1))
            whenever(getPhotos.invoke()).thenReturn(photos)

            viewModel.model.observeForever(observer)

            viewModel.onPermissionRequested()

            verify(observer).onChanged(UiModel.Content(photos))
        }
    }

}