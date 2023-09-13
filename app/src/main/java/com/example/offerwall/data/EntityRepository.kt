package com.example.offerwall.data

import android.util.Log
import com.example.offerwall.network.Entity
import com.example.offerwall.network.EntityApiService
import com.example.offerwall.network.Ids

interface EntityRepository {
    suspend fun getIds(): Ids
    suspend fun getEntity(id: Int): Entity
}

private const val ERROR_TAG = "Error download from internet"

class NetworkEntityRepository(
    private val entityApiService: EntityApiService
) : EntityRepository {
    override suspend fun getIds(): Ids {
        try {
            val response = entityApiService.getEntitiesIds()
            if (response.isSuccessful) return response.body()!!
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.message, e)
        }

        return Ids()
    }
    override suspend fun getEntity(id: Int): Entity {
        try {
            val response = entityApiService.getEntity(id)
            if (response.isSuccessful) return response.body()!!
        } catch (e: Exception) {
            Log.e(ERROR_TAG, e.message, e)
        }

        return Entity(type = "error")
    }
}