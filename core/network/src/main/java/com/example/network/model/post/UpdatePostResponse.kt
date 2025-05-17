package com.example.network.model.post

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class UpdatePostResponse(
    val id: Int,
    val postType: String,
    val viewCount: Int,
    val emotionCount: Map<String, Int>?,
    val title: String,
    val content: String,
    val providerId: String,
    val nickname: String,
    val imageUrls: List<String>,
    val profileImageUrl: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isOwner: Boolean,
    val isVerified: Boolean,
)
