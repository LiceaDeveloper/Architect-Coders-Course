package com.liceadev.data

import com.liceadev.data.PermissionChecker.Permission.COARSE_LOCATION
import com.liceadev.data.source.LocationDataSource

class CountryRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        const val DEFAULT_COUNTRY = "Mexico"
    }


    suspend fun findCountry(): String {
        val permissionGranted = permissionChecker.check(COARSE_LOCATION)
        return if (permissionGranted) {
            locationDataSource.getCountry() ?: DEFAULT_COUNTRY
        } else {
            DEFAULT_COUNTRY
        }
    }
}



interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}