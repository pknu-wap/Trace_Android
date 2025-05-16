package com.example.network.source.post

import android.os.Build
import com.example.domain.model.post.WritePostType
import com.example.network.api.TraceApi
import com.example.network.model.post.AddPostRequest
import com.example.network.model.post.AddPostResponse
import com.example.network.model.post.GetPostResponse
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

        val requestImage = images?.map {  image ->
            val body = image.readBytes().toRequestBody(mediaType)
            MultipartBody.Part.createFormData(
                name = "imageFile",
                filename = imageFileName,
                body = body
            )
        }

        return traceApi.addPost(
            addPostRequest = requestBody,
            imageFile = requestImage
        )

    }

    companion object {
        private const val WEBP_MEDIA_TYPE = "image/webp"
        private const val JPEG_MEDIA_TYPE = "image/jpeg"
    }

}