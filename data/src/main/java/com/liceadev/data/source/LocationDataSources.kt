package com.liceadev.data.source

interface LocationDataSources {
    suspend fun getCountry(): String?
}