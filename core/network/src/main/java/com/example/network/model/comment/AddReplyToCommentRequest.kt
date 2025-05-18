package com.example.network.model.comment

import kotlinx.serialization.Serializable

@Serializable
data class AddReplyToCommentRequest(
    val content : String,
)
