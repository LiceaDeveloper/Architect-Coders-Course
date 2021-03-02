package com.liceadev.data

import com.liceadev.data.source.LocalDataSource
import com.liceadev.data.source.RemoteDataSource
import com.liceadev.domain.Photo
import com.liceadev.testshared.mockedPhoto
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotosRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var countryRepository: CountryRepository

    lateinit var photosRepository: PhotosRepository

    private val apiKey = "1a2b3c4d"

    @Before
    fun setUp() {
        photosRepository =
            PhotosRepository(localDataSource, remoteDataSource, countryRepository, apiKey)
    }


    @Test
    fun `findById call local data sources`() {
        runBlocking {
            //GIVEN
            val photo = mockedPhoto.copy(id = 5)
            whenever(localDataSource.findById(5)).thenReturn(photo)

            //WHEN
            val result = photosRepository.findById(5)

            //THEN
            assertEquals(photo, result)
        }
    }

    @Test
    fun `update local data source`() {
        runBlocking {
            val photo = mockedPhoto.copy(id = 1)
            photosRepository.update(photo)
            verify(localDataSource).update(photo)
        }
    }


    @Test
    fun `getPhotos gets from local data source first`() {
        runBlocking {

            val localPhotos = listOf(mockedPhoto.copy(1))
            whenever(localDataSource.isEmpty()).thenReturn(false)
            whenever(localDataSource.getPhotos()).thenReturn(localPhotos)

            val result = photosRepository.getPhotos()

            assertEquals(localPhotos, result)
        }
    }

    @Test
    fun `getPhotos saves remote data to local`() {
        runBlocking {

            val remotePhotos = listOf(mockedPhoto.copy(2))
            whenever(localDataSource.isEmpty()).thenReturn(true)
            whenever(remoteDataSource.getPhotos(any(), any())).thenReturn(remotePhotos)
            whenever(countryRepository.findCountry()).thenReturn("Mexico")

            photosRepository.getPhotos()

            verify(localDataSource).savePhotos(remotePhotos)
        }
    }
}