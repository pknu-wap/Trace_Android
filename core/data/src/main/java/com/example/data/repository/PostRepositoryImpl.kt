package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.data.image.ImageResizer
import com.example.domain.model.post.EmotionCount
import com.example.domain.model.post.PostDetail
import com.example.domain.model.post.PostType
import com.example.domain.model.post.WritePostType
import com.example.domain.repository.PostRepository
import com.example.network.source.post.PostDataSource
import jakarta.inject.Inject
import kotlinx.datetime.toJavaLocalDateTime

class PostRepositoryImpl @Inject constructor(
    private val postDataSource: PostDataSource,
    private val imageResizer: ImageResizer
) : PostRepository {

    override suspend fun getPost(postId: Int): Result<PostDetail> = suspendRunCatching {
        val response = postDataSource.getPost(postId).getOrThrow()

        PostDetail(
            postId = response.id,
            postType = PostType.fromString(response.postType),
            viewCount = response.viewCount,
            emotionCount = EmotionCount.fromMap(response.emotionCount),
            title = response.title,
            content = response.content,
            providerId = response.providerId,
            nickname = response.nickname,
            images = response.imageUrls,
            profileImageUrl = response.profileImageUrl,
            createdAt = response.createdAt.toJavaLocalDateTime(),
            updatedAt = response.updatedAt.toJavaLocalDateTime(),
            comments = emptyList(),
            isOwner = response.isOwner,
            isVerified = response.isVerified,
        )
    }

    override suspend fun addPost(
        postType: WritePostType,
        title: String,
        content: String,
        images: List<String>?
    ): Result<Int> = suspendRunCatching {
        val imageStreams = images?.mapIndexed { index, imageUrl ->
            imageResizer.resizeImage(imageUrl)
        }

        val response = postDataSource.addPost(postType, title, content, imageStreams).getOrThrow()

        response.id
    }

    override suspend fun updatePost(
        postId : Int,
        title: String,
        content: String,
        images: List<String>?
    ): Result<Int> = suspendRunCatching {
//        val imageStreams = images?.mapIndexed { index, imageUrl ->
//            imageResizer.resizeImage(imageUrl)
//        }

        val response = postDataSource.updatePost(postId, title, content, null).getOrThrow()

        response.id
    }

    override suspend fun deletePost(): Result<Unit> {
        return Result.success(Unit)
    }

}
