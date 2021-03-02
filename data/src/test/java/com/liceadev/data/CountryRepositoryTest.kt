package com.liceadev.data

import com.liceadev.data.CountryRepository.Companion.DEFAULT_COUNTRY
import com.liceadev.data.source.LocationDataSource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.junit.runner.RunWith
import com.liceadev.data.PermissionChecker.Permission.COARSE_LOCATION
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class CountryRepositoryTest {

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    private lateinit var countryRepository: CountryRepository

    @Before
    fun setUp() {
        countryRepository = CountryRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `findCountry permission not granted`() {
        runBlocking {
            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(false)
            val result = countryRepository.findCountry()
            assertEquals(DEFAULT_COUNTRY, result)
        }

    }

    @Test
    fun `findCountry permission granted`() {
        runBlocking {
            whenever(permissionChecker.check(COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.getCountry()).thenReturn("España")

            val result = countryRepository.findCountry()

            assertEquals("España", result)
        }

    }
}