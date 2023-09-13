package com.example.offerwall.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EntityApiService {
    @GET("entities/getAllIds")
    suspend fun getEntitiesIds(): Response<Ids>

    @GET("object/{id}")
    suspend fun getEntity(@Path(value = "id") id: Int): Response<Entity>
}