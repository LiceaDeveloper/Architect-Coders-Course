package com.liceadev.architectcoders.model

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Application
import android.location.Geocoder
import android.location.Location

class CountryRepository(private val application: Application) {
    companion object {
        private const val DEFAULT_COUNTRY = "Mexico"
    }

    private val locationDataSources: LocationDataSources = PlayServicesLocation(application)
    private val permissionChecker = PermissionChecker(application, ACCESS_COARSE_LOCATION)

    suspend fun findLastCountry(): String = findLastLocation()

    private suspend fun findLastLocation(): String {
        val permissionGranted = permissionChecker.check()
        return if (permissionGranted) getCountryFromLocation(locationDataSources.findLastLocation()) else DEFAULT_COUNTRY
    }

    private fun getCountryFromLocation(location: Location?): String {
        val geocode = Geocoder(application)
        val fromLocation = location?.let {
            geocode.getFromLocation(location.latitude, location.longitude, 1)
        }
        return fromLocation?.firstOrNull()?.countryName ?: DEFAULT_COUNTRY
    }
}