package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.post.Emotion
import com.example.domain.model.post.PostDetail
import com.example.domain.model.post.PostFeed
import com.example.domain.model.post.TabType
import com.example.domain.model.post.WritePostType
import kotlinx.coroutines.flow.Flow


interface PostRepository {
    fun getPostPagingFlow(tabType: TabType): Flow<PagingData<PostFeed>>

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

    suspend fun toggleEmotion(postId: Int, emotionType: Emotion): Result<Boolean>
}