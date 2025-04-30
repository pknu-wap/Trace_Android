package com.example.domain.repository

interface PostRepository {
    suspend fun getPost() : Result<Unit>
    suspend fun addPost(title : String, content: String, images: List<String>?) : Result<Unit>
    suspend fun updatePost() : Result<Unit>
    suspend fun deletePost() : Result<Unit>
}