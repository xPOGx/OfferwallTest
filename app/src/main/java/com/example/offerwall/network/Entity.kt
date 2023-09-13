package com.example.offerwall.network

import kotlinx.serialization.Serializable

/**
 * Holds object info
 * @param id object id
 * @param type object type text, webview, image and game
 * @param message optional for text [type]
 * @param url optional for webview and image [type]
 */
@Serializable
data class Entity(
    val id: Int = 0,
    val type: String = "",
    val message: String = "",
    val url: String = ""
)

/**
 * Hold list ids of [Entity]
 * @param data list of [Keys]
 */
@Serializable
data class Ids(
    val data: List<Keys> = listOf(),
)

/**
 * Hold object id
 * @param id object id
 */
@Serializable
data class Keys(
    val id: Int = 0
)