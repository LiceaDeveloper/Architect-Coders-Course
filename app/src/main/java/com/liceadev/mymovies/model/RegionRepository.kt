package com.liceadev.mymovies.model

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.annotation.SuppressLint
import android.app.Activity
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class RegionRepository(private val activity: Activity) {
    companion object {
        private const val DEFAULT_REGION = "US"
    }

    private val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
    private val coarsePermissionChecker = PermissionChecker(activity, ACCESS_COARSE_LOCATION)

    suspend fun findLastRegion(): String = findLastLocation()

    private suspend fun findLastLocation(): String {
        val hasPermission = coarsePermissionChecker.request()
        return if (hasPermission) getRegion() else DEFAULT_REGION
    }

    @SuppressLint("MissingPermission")
    suspend fun getRegion(): String =
        suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                continuation.resume(getRegionFromLocation(it.result))
            }
        }

    private fun getRegionFromLocation(location: Location?): String {
        val geocode = Geocoder(activity)
        val fromLocation = location?.let {
            geocode.getFromLocation(location.latitude, location.longitude, 1)
        }
        return fromLocation?.firstOrNull()?.countryCode ?: DEFAULT_REGION
    }
}
