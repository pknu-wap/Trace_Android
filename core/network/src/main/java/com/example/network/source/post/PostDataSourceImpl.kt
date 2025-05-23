package com.example.network.source.post

import android.os.Build
import android.util.Log
import com.example.domain.model.post.Emotion
import com.example.domain.model.post.TabType
import com.example.domain.model.post.WritePostType
import com.example.network.api.TraceApi
import com.example.network.model.post.AddPostRequest
import com.example.network.model.post.AddPostResponse
import com.example.network.model.post.GetPostResponse
import com.example.network.model.post.GetPostsRequest
import com.example.network.model.post.GetPostsResponse
import com.example.network.model.post.ToggleEmotionRequest
import com.example.network.model.post.ToggleEmotionResponse
import com.example.network.model.post.UpdatePostRequest
import com.example.network.model.post.UpdatePostResponse
import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

class PostDataSourceImpl @Inject constructor(
    private val traceApi: TraceApi,
) : PostDataSource {
    override suspend fun getPosts(
        cursorDateTime: LocalDateTime?,
        cursorId: Int?,
        size: Int,
        postType: TabType
    ): Result<GetPostsResponse> = traceApi.getPosts(
        getPostsRequest = GetPostsRequest(
            cursorDateTime = cursorDateTime,
            cursorId = cursorId,
            size = size,
            postType = if (postType.equals(TabType.ALL)) null else postType.name
        )
    )

    override suspend fun getPost(postId: Int): Result<GetPostResponse> = traceApi.getPost(postId)

    override suspend fun addPost(
        postType: WritePostType,
        title: String,
        content: String,
        images: List<InputStream>?
    ): Result<AddPostResponse> {
        val jsonString = Json.encodeToString(
            AddPostRequest(
                postType = postType.name,
                title = title,
                content = content
            )
        )

        val requestBody = jsonString
            .toRequestBody("application/json; charset=utf-8".toMediaType())

        val (imageFileExtension, imageFileName) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WEBP_MEDIA_TYPE to "profile_${UUID.randomUUID()}.webp"
        } else {
            JPEG_MEDIA_TYPE to "profile_${UUID.randomUUID()}.jpg"
        }

        val mediaType = imageFileExtension.toMediaTypeOrNull()
            ?: throw IllegalArgumentException("Invalid media type: $imageFileExtension")

        val requestImage = images?.map { image ->
            val body = image.readBytes().toRequestBody(mediaType)
            MultipartBody.Part.createFormData(
                name = "imageFiles",
                filename = imageFileName,
                body = body
            )
        }

        Log.d("traceRequest", requestImage.toString() + " size : ${requestImage?.size}")

        return traceApi.addPost(
            addPostRequest = requestBody,
            imageFile = requestImage
        )

    }

    override suspend fun updatePost(
        postId: Int,
        title: String,
        content: String,
        images: List<InputStream>?
    ): Result<UpdatePostResponse> =
        traceApi.updatePost(
            postId = postId,
            updatePostRequest = UpdatePostRequest(
                title = title,
                content = content
            )
        )

    override suspend fun deletePost(postId: Int): Result<Unit> = traceApi.deletePost(postId)

    override suspend fun toggleEmotion(
        postId: Int,
        emotionType: Emotion
    ): Result<ToggleEmotionResponse> =
        traceApi.toggleEmotion(
            toggleEmotionRequest = ToggleEmotionRequest(
                postId = postId,
                emotionType = emotionType.name
            )
        )

    companion object {
        private const val WEBP_MEDIA_TYPE = "image/webp"
        private const val JPEG_MEDIA_TYPE = "image/jpeg"
    }

}