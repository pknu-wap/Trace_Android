package com.example.domain.repository

import com.example.domain.model.post.PostDetail

interface PostRepository {
    suspend fun getPost(postId : Int) : Result<PostDetail>
    suspend fun addPost(title : String, content: String, images: List<String>?) : Result<Int>
    suspend fun updatePost() : Result<Unit>
    suspend fun deletePost() : Result<Unit>
}