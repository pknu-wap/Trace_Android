package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.data.image.ImageResizer
import com.example.domain.model.post.FeelingCount
import com.example.domain.model.post.PostDetail
import com.example.domain.model.post.PostType
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
            postType = PostType.GOOD_DEED,
            nickname = response.nickname,
            title = response.title,
            content = response.content,
            profileImageUrl = null,
            isVerified = false,
            createdAt = response.createdAt.toJavaLocalDateTime(),
            viewCount = (1..1000).random(),
            comments = emptyList(),
            feelingCount = FeelingCount(
                heartWarmingCount = 0,
                gratefulCount = 0,
                likeableCount = 0,
                touchingCount = 0,
                impressiveCount = 0
            ),
            images = response.imageUrls,
            postId = 0,
            userId = 0
        )
    }

    override suspend fun addPost(
        title: String,
        content: String,
        images: List<String>?
    ): Result<Int> = suspendRunCatching {
        val imageStreams = images?.mapIndexed { index, imageUrl ->
            imageResizer.resizeImage(imageUrl)
        }

        val response = postDataSource.addPost(title, content, imageStreams).getOrThrow()

        response.id
    }

    override suspend fun updatePost(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun deletePost(): Result<Unit> {
        return Result.success(Unit)
    }

}
