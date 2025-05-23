package com.example.network.model.post

import com.example.domain.model.post.PostFeed
import com.example.domain.model.post.PostType
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetPostsResponse(
    val content : List<PostContent>,
    val hasNext : Boolean,
    val cursor : Cursor?,
) {
    fun toDomain() : List<PostFeed> {
        return content.map {
            PostFeed(
                postId = it.postId,
                providerId = it.providerId,
                postType = PostType.fromString(it.postType),
                title = it.title,
                content = it.content,
                nickname = it.nickname,
                viewCount = it.viewCount,
                commentCount = it.commentCount,
                isVerified = it.verified,
                imageUrl = it.imageUrl,
                createdAt = it.createdAt.toJavaLocalDateTime(),
                updatedAt = it.updatedAt.toJavaLocalDateTime()
            )
        }
    }
}

@Serializable
data class PostContent(
    val postId: Int,
    val providerId: String,
    val postType: String,
    val title: String,
    val content: String,
    val nickname: String,
    val profileImageUrl: String,
    val imageUrl: String,
    val viewCount: Int,
    val commentCount: Int,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val verified: Boolean
)

@Serializable
data class Cursor(
    val dateTime : LocalDateTime,
    val id : Int,
)