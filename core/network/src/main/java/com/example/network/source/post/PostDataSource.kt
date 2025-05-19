package com.example.network.source.post


import com.example.domain.model.post.Emotion
import com.example.domain.model.post.WritePostType
import com.example.network.model.post.AddPostResponse
import com.example.network.model.post.GetPostResponse
import com.example.network.model.post.ToggleEmotionResponse
import com.example.network.model.post.UpdatePostResponse
import java.io.InputStream

interface PostDataSource {
    suspend fun getPost(
        postId : Int
    ) : Result<GetPostResponse>

    suspend fun addPost(
        postType : WritePostType, title: String, content: String, images: List<InputStream>?
    ): Result<AddPostResponse>

    suspend fun updatePost(
        postId : Int, title: String, content: String, images: List<InputStream>?,
    ) : Result<UpdatePostResponse>

    suspend fun deletePost(
        postId : Int,
    ) : Result<Unit>

    suspend fun toggleEmotion(
        postId : Int,
        emotionType : Emotion
    ) : Result<ToggleEmotionResponse>
}
