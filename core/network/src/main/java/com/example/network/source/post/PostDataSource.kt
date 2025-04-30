package com.example.network.source.post


import com.example.network.model.post.AddPostResponse
import com.example.network.model.post.GetPostResponse
import java.io.InputStream

interface PostDataSource {
    suspend fun getPost(
        postId : Int
    ) : Result<GetPostResponse>

    suspend fun addPost(
        title: String, content: String, images: List<InputStream>?
    ): Result<AddPostResponse>
}
