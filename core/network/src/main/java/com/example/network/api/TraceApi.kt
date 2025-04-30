package com.example.network.api

import com.example.network.model.auth.LoginKakaoRequest
import com.example.network.model.auth.LoginKakaoResponse
import com.example.network.model.auth.TokenResponse
import com.example.network.model.token.RefreshTokenRequest
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface TraceApi {
    @POST("/api/v1/auth/oauth/login")
    suspend fun loginKakao(@Body loginKakaoRequest: LoginKakaoRequest): Result<LoginKakaoResponse>

    @Multipart
    @POST("/api/v1/auth/oauth/signup")
    suspend fun registerUser(
        @Part("request") registerUserRequest: RequestBody,
        @Part profileImage: MultipartBody.Part? = null
    ): Result<TokenResponse>

    @HTTP(method = "GET", path = "/api/f1/auth/refresh", hasBody = true)
    suspend fun refreshToken(@Body refreshTokenRequest: RefreshTokenRequest): Result<TokenResponse>

    @Multipart
    @POST("/api/v1/api/posts")
    suspend fun addPost(
        @Part("request") addPostRequest: RequestBody,
        @Part imageFile: List<MultipartBody.Part>? = null
    ) : Result<Unit>
}
