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
class FindPhotoByIdTest {

    @Mock
    lateinit var photosRepository: PhotosRepository

    lateinit var findPhotoById: FindPhotoById

    @Mock
    lateinit var mockedPhoto: Photo

    @Before
    fun setUp() {
        findPhotoById = FindPhotoById(photosRepository)
    }


    @Test
    fun `invoke call photosRepository`() {
        runBlocking {
            val photo = mockedPhoto.copy(id = 1)
            whenever(photosRepository.findById(1)).thenReturn(photo)

            val result = findPhotoById.invoke(1)

            Assert.assertEquals(photo, result)
        }
    }
}