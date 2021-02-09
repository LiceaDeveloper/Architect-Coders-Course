package com.liceadev.architectcoders.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.liceadev.domain.Photo
import com.liceadev.domain.User
import com.liceadev.usecases.FindPhotoById
import com.liceadev.usecases.TogglePhotoFavorite
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

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findPhotoById: FindPhotoById

    @Mock
    lateinit var togglePhotoFavorite: TogglePhotoFavorite

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    private lateinit var vm: DetailViewModel

    private val mockedPhoto by lazy {
        Photo(
            id = 0,
            description = "description",
            likes = 0,
            urlFull = "urlFull",
            urlThumb = "urlThumb",
            user = mockedUser,
            favorite = false
        )
    }

    private val mockedUser = User(
        userId = "userId",
        totalPhotos = 0,
        totalLikes = 0,
        portfolioUrl = "portfolioUrl",
        profileImage = "profileImage",
        name = "name",
        location = "Mexico",
        username = "userName"
    )

    @Before
    fun setUp() {
        vm = DetailViewModel(1, findPhotoById, togglePhotoFavorite, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the photo`() {

        runBlocking {
            val photo = mockedPhoto.copy(id = 1)
            whenever(findPhotoById.invoke(1)).thenReturn(photo)

            vm.model.observeForever(observer)

            verify(observer).onChanged(DetailViewModel.UiModel(photo))
        }
    }

    @Test
    fun `when favorite clicked, the togglePhotoFavorite use case is invoked`() {
        runBlocking {
            val photo = mockedPhoto.copy(id = 1)
            whenever(findPhotoById.invoke(1)).thenReturn(photo)
            whenever(togglePhotoFavorite.invoke(photo)).thenReturn(photo.copy(favorite = !photo.favorite))
            vm.model.observeForever(observer)

            vm.onFavoriteClicked()

            verify(togglePhotoFavorite).invoke(photo)
        }
    }
}