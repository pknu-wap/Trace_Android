package com.example.network.source.post

import kotlinx.serialization.Serializable
import java.io.InputStream

interface PostDataSource {
    suspend fun addPost(
        title: String, content: String, images: List<InputStream>?
    ): Result<Unit>
}
