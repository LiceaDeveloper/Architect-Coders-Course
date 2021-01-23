package com.liceadev.data

import com.liceadev.data.PermissionChecker.Permission.COARSE_LOCATION
import com.liceadev.data.source.LocationDataSources

class CountryRepository(
    private val locationDataSources: LocationDataSources,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        private const val DEFAULT_COUNTRY = "Mexico"
    }


    suspend fun findCountry(): String {
        val permissionGranted = permissionChecker.check(COARSE_LOCATION)
        return if (permissionGranted) {
            locationDataSources.getCountry() ?: DEFAULT_COUNTRY
        } else {
            DEFAULT_COUNTRY
        }
    }
}



interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}