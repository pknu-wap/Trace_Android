package com.example.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class AddPostRequest(
    val title : String,
    val content : String
)
