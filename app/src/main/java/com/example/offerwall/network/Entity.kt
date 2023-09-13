package com.example.offerwall.network

import kotlinx.serialization.Serializable

@Serializable
data class Entity(
    val id: Int = 0,
    val type: String = "",
    val message: String = "",
    val url: String = ""
)

@Serializable
data class Ids(
    val data: List<Keys> = listOf(),
)

@Serializable
data class Keys(
    val id: Int = 0
)