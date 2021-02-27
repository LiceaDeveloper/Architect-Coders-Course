package com.liceadev.architectcoders.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.liceadev.architectcoders.FakeLocalDataSource
import com.liceadev.architectcoders.defaultFakePhotos
import com.liceadev.architectcoders.initMockedDi
import com.liceadev.data.source.LocalDataSource
import com.liceadev.testshared.mockedPhoto
import com.liceadev.usecases.FindPhotoById
import com.liceadev.usecases.TogglePhotoFavorite
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    private lateinit var viewModel: DetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (id: Int) -> DetailViewModel(id, get(), get(), get()) }
            factory { FindPhotoById(get()) }
            factory { TogglePhotoFavorite(get()) }
        }

        initMockedDi(vmModule)
        viewModel = get { parametersOf(1) }

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.photos = defaultFakePhotos
    }

    @Test
    fun `observing LiveData finds the photo`() {
        viewModel.model.observeForever(observer)

        verify(observer).onChanged(DetailViewModel.UiModel(mockedPhoto.copy(1)))
    }

    @Test
    fun `favorite is updated in local data source`() {
        viewModel.model.observeForever(observer)

        viewModel.onFavoriteClicked()

        runBlocking {
            assertTrue(localDataSource.findById(1).favorite)
        }
    }
}