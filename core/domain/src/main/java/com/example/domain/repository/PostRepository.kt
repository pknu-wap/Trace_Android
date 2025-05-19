package com.example.domain.repository

import com.example.domain.model.post.Emotion
import com.example.domain.model.post.PostDetail
import com.example.domain.model.post.WritePostType

interface PostRepository {
    suspend fun getPost(postId: Int): Result<PostDetail>

    suspend fun addPost(
        postType: WritePostType,
        title: String,
        content: String,
        images: List<String>?
    ): Result<Int>

    suspend fun updatePost(
        postId: Int,
        title: String,
        content: String,
        images: List<String>?
    ): Result<Int>

    suspend fun deletePost(postId: Int): Result<Unit>

    suspend fun toggleEmotion(postId : Int, emotionType : Emotion) : Result<Boolean>
}