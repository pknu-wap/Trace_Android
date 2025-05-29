package com.example.network.model.post

import com.example.domain.model.post.Emotion
import com.example.domain.model.post.EmotionCount
import com.example.domain.model.post.PostDetail
import com.example.domain.model.post.PostType
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class PostResponse(
    val id: Int,
    val postType: String,
    val emotionCount: Map<String, Int>?,
    val viewCount: Int,
    val title: String,
    val content: String,
    val providerId: String,
    val nickname: String,
    val imageUrls: List<String>,
    val profileImageUrl: String?,
    val yourEmotionType : String?,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val isOwner: Boolean,
    val isVerified: Boolean,
) {
    fun toDomain(): PostDetail {
        return PostDetail(
            postId = id,
            postType = PostType.fromString(postType),
            viewCount = viewCount,
            emotionCount = emotionCount?.let { EmotionCount.fromMap(emotionCount) } ?: EmotionCount(
                0,
                0,
                0,
                0
            ),
            title = title,
            content = content,
            providerId = providerId,
            nickname = nickname,
            images = imageUrls,
            profileImageUrl = profileImageUrl,
            yourEmotionType = Emotion.fromString(yourEmotionType),
            createdAt = createdAt.toJavaLocalDateTime(),
            updatedAt = updatedAt.toJavaLocalDateTime(),
            isOwner = isOwner,
            isVerified = isVerified,
        )
    }
}