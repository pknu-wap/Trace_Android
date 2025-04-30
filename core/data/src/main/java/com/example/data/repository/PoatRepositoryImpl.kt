package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.data.image.ImageResizer
import com.example.domain.repository.PostRepository
import com.example.network.source.post.PostDataSource
import jakarta.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val postDataSource: PostDataSource,
    private val imageResizer: ImageResizer
) : PostRepository {

    override suspend fun getPost(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun addPost(
        title: String,
        content: String,
        images: List<String>?
    ): Result<Unit> = suspendRunCatching {
        val imageStreams = images?.mapIndexed { index, imageUrl ->
            imageResizer.resizeImage(imageUrl)
        }

        postDataSource.addPost(title, content, imageStreams)
    }

    override suspend fun updatePost(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun deletePost(): Result<Unit> {
        return Result.success(Unit)
    }

}