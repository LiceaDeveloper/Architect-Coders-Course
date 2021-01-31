package com.liceadev.architectcoders.model

import android.annotation.SuppressLint
import android.app.Application
import android.location.Geocoder
import android.location.Location
import com.google.android.gms.location.LocationServices
import com.liceadev.data.source.LocationDataSource
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume


class PlayServicesLocation(application: Application) : LocationDataSource {
    val geocoder = Geocoder(application)
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun getCountry(): String? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                val location = it.result
                continuation.resume(getCountryFromLocation(location))
            }
        }


    private fun getCountryFromLocation(location: Location?): String? {
        val fromLocation = location?.let {
            geocoder.getFromLocation(location.latitude, location.longitude, 1)
        }
        return fromLocation?.firstOrNull()?.countryName
    }
}