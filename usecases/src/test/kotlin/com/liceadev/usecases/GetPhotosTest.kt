package com.liceadev.usecases

import com.liceadev.data.PhotosRepository
import com.liceadev.domain.Photo
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPhotosTest {

    @Mock
    lateinit var photosRepository: PhotosRepository

    lateinit var getPhotos: GetPhotos

    @Mock
    lateinit var mockedPhoto: Photo

    @Before
    fun setUp() {
        getPhotos = GetPhotos(photosRepository)
    }


    @Test
    fun `invoke call photosRepository`() {
        runBlocking {
            val photos = listOf(mockedPhoto.copy(id = 1))
            whenever(photosRepository.getPhotos()).thenReturn(photos)

            val result = getPhotos.invoke()

            Assert.assertEquals(photos, result)
        }
    }
}