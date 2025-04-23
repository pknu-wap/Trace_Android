package com.example.network.source.auth

import android.os.Build
import com.example.network.api.TraceApi
import com.example.network.model.auth.LoginKakaoRequest
import com.example.network.model.auth.LoginKakaoResponse
import com.example.network.model.auth.RegisterUserRequest
import com.example.network.model.auth.TokenResponse
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.InputStream
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataSourceImpl @Inject constructor(
    private val traceApi: TraceApi,
) : AuthDataSource {
    override suspend fun loginKakao(idToken: String): Result<LoginKakaoResponse> =
        traceApi.loginKakao(
            loginKakaoRequest = LoginKakaoRequest(
                idToken = idToken
            )
        )

    override suspend fun registerUser(
        idToken: String,
        nickname: String,
        profileImage: InputStream?
    ): Result<TokenResponse> {
        val jsonString = Json.encodeToString(
            RegisterUserRequest(
                idToken = idToken,
                nickname = nickname
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

        val requestImage = profileImage?.let {
            MultipartBody.Part.createFormData(
                name = "profileImage",
                filename = imageFileName,
                body = profileImage.readBytes().toRequestBody(mediaType)
            )
        }

        return traceApi.registerUser(
            registerUserRequest = requestBody,
            profileImage = requestImage
        )
    }

    companion object {
        private const val WEBP_MEDIA_TYPE = "image/webp"
        private const val JPEG_MEDIA_TYPE = "image/jpeg"
    }

}