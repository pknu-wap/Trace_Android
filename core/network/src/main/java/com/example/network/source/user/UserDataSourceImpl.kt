package com.example.network.source.user

import android.os.Build
import com.example.network.api.TraceApi
import com.example.network.model.user.LoadUserInfoResponse
import com.example.network.model.user.UpdateNicknameRequest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val traceApi: TraceApi,
) : UserDataSource {
    override suspend fun loadUserInfo(): Result<LoadUserInfoResponse> = traceApi.loadUserInfo()
    override suspend fun updateNickname(nickname: String): Result<LoadUserInfoResponse> =
        traceApi.updateNickname(
            updateNicknameRequest = UpdateNicknameRequest(nickname)
        )

    override suspend fun updateProfileImage(profileImage: InputStream?): Result<LoadUserInfoResponse> {
        val (imageFileExtension, imageFileName) = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            WEBP_MEDIA_TYPE to "profile_${UUID.randomUUID()}.webp"
        } else {
            JPEG_MEDIA_TYPE to "profile_${UUID.randomUUID()}.jpg"
        }

        val mediaType = imageFileExtension.toMediaTypeOrNull()
            ?: throw IllegalArgumentException("Invalid media type: $imageFileExtension")

        val requestImage = profileImage?.let {
            MultipartBody.Part.createFormData(
                name = "profileImage",
                filename = imageFileName,
                body = profileImage.readBytes().toRequestBody(mediaType)
            )
        }

        return traceApi.updateProfileImage(requestImage)
    }

    companion object {
        private const val WEBP_MEDIA_TYPE = "image/webp"
        private const val JPEG_MEDIA_TYPE = "image/jpeg"
    }
}