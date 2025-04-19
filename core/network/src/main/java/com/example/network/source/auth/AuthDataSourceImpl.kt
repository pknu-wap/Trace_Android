package com.example.network.source.auth

import com.example.network.api.TraceApi
import com.example.network.model.auth.LoginKakaoRequest
import com.example.network.model.auth.LoginKakaoResponse
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

//    override suspend fun registerUser(
//        idToken: String,
//        nickname: String,
//        profileImageUrl: String?
//    ): Result<TokenResponse> {
//
//        val jsonString = Json.encodeToString(
//            RegisterUserRequest(
//                idToken    = idToken,
//                nickname   = nickname
//            )
//        )
//
//        val profileImageFile = profileImageUrl
//            ?.let { url ->
//                val input = context.contentResolver.openInputStream(url)
//                val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir)
//                input.use { it.copyTo(tempFile.outputStream()) }
//            }
//
//        val requestBody = jsonString
//            .toRequestBody("application/json; charset=utf-8".toMediaType())
//
//        val imagePart: MultipartBody.Part? = profileImageFile?.let { file ->
//            MultipartBody.Part.createFormData(
//                "profileImage",
//                file.name,
//                file.asRequestBody("image/jpeg".toMediaType())
//            )
//        }
//
//        traceApi.registerUser(
//            registerUserRequest  = requestBody,
//            profileImage = imagePart
//        )
//    }

}