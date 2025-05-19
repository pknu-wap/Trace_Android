package com.example.network.model.post

import kotlinx.serialization.Serializable

@Serializable
data class ToggleEmotionRequest(
    val postId : Int,
    val emotionType: String,
)
