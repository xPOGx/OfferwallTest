package com.example.offerwall.data

import android.content.Context
import com.example.offerwall.network.EntityApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

interface AppContainer {
    val entityRepository: EntityRepository
}

class AppDataContainer(
    private val context: Context
) : AppContainer {
    private val baseUrl = "https://demo3005513.mockable.io/api/v1/"

    private val json = Json {
        ignoreUnknownKeys = true
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: EntityApiService by lazy {
        retrofit.create(EntityApiService::class.java)
    }

    override val entityRepository: EntityRepository by lazy {
        NetworkEntityRepository(retrofitService)
    }
}