package com.liceadev.mymovies.model

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.location.Geocoder
import android.location.Location

class RegionRepository(private val activity: Activity) {
    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val locationDataSources: LocationDataSources = PlayServicesLocation(activity)
    private val permissionChecker = PermissionChecker(activity, ACCESS_COARSE_LOCATION)

    suspend fun findLastRegion(): String = findLastLocation()

    private suspend fun findLastLocation(): String {
        val permissionGranted = permissionChecker.request()
        return if (permissionGranted) getRegionFromLocation(locationDataSources.findLastLocation()) else DEFAULT_REGION
    }

    private fun getRegionFromLocation(location: Location?): String {
        val geocode = Geocoder(activity)
        val fromLocation = location?.let {
            geocode.getFromLocation(location.latitude, location.longitude, 1)
        }
        return fromLocation?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}