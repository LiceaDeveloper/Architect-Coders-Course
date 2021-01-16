package com.liceadev.architectcoders.model

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.location.Geocoder
import android.location.Location

class CountryRepository(private val activity: Activity) {
    companion object {
        private const val DEFAULT_COUNTRY = "USA"
    }

    private val locationDataSources: LocationDataSources = PlayServicesLocation(activity)
    private val permissionChecker = PermissionChecker(activity, ACCESS_COARSE_LOCATION)

    suspend fun findLastCountry(): String = findLastLocation()

    private suspend fun findLastLocation(): String {
        val permissionGranted = permissionChecker.request()
        return if (permissionGranted) getCountryFromLocation(locationDataSources.findLastLocation()) else DEFAULT_COUNTRY
    }

    private fun getCountryFromLocation(location: Location?): String {
        val geocode = Geocoder(activity)
        val fromLocation = location?.let {
            geocode.getFromLocation(location.latitude, location.longitude, 1)
        }
        return fromLocation?.firstOrNull()?.countryName ?: DEFAULT_COUNTRY
    }
}