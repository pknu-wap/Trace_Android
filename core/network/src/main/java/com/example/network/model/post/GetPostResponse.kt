package com.example.network.model.post

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetPostResponse(
    val id: Int,
    val title: String,
    val content: String,
    val userId: Int,
    val nickname: String,
    val imageUrls : List<String>,
    val createdAt : LocalDateTime,
    val updatedAt : LocalDateTime
)
