package com.example.network.api

import com.example.network.model.auth.LoginKakaoRequest
import com.example.network.model.auth.LoginKakaoResponse
import com.example.network.model.auth.RefreshTokenRequest
import com.example.network.model.auth.RegisterUserRequest
import com.example.network.model.auth.TokenResponse
import retrofit2.http.Body
import retrofit2.http.HTTP
import retrofit2.http.POST

interface TraceApi {
    @POST("/api/v1/auth/oauth/login")
    suspend fun loginKakao(@Body loginKakaoRequest: LoginKakaoRequest) : Result<LoginKakaoResponse>

    @POST("/api/v1/auth/oauth/signup")
    suspend fun registerUser(@Body registerUserRequest: RegisterUserRequest) : Result<TokenResponse>

    @HTTP(method = "GET", path = "/api/f1/auth/refresh", hasBody = true)
    suspend fun refreshToken(@Body refreshToeknRequest : RefreshTokenRequest) : Result<TokenResponse>
}