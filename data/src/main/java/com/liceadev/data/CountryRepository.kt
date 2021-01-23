package com.liceadev.data

import com.liceadev.data.PermissionChecker.Permission.COARSE_LOCATION

class CountryRepository(
    private val locationDataSources: LocationDataSources,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        private const val DEFAULT_COUNTRY = "Mexico"
    }


    suspend  fun findCountry(): String {
        val permissionGranted = permissionChecker.check(COARSE_LOCATION)
        return if (permissionGranted) locationDataSources.getCountry() else DEFAULT_COUNTRY
    }
}

interface LocationDataSources {
     fun getCountry(): String
}

interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}