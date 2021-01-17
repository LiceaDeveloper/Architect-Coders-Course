package com.liceadev.architectcoders.model

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

interface LocationDataSources {
    suspend fun findLastLocation(): Location?
}

class PlayServicesLocation(application: Application) : LocationDataSources {
    private val fusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(application)

    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                continuation.resume(it.result)
            }
        }
}