package com.liceadev.data.source

interface LocationDataSource {
    suspend fun getCountry(): String?
}