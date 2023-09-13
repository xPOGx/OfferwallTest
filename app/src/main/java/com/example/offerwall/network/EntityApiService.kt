package com.example.offerwall.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface EntityApiService {
    /**
     * Get [List] of [Ids] from Api
     */
    @GET("entities/getAllIds")
    suspend fun getEntitiesIds(): Response<Ids>

    /**
     * Get [Entity] from Api by id
     * @param id object id
     */
    @GET("object/{id}")
    suspend fun getEntity(@Path(value = "id") id: Int): Response<Entity>
}