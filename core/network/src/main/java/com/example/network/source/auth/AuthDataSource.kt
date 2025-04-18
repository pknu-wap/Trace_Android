package com.example.network.source.auth

import com.example.network.model.auth.LoginKakaoRequest
import com.example.network.model.auth.LoginKakaoResponse
import com.example.network.model.auth.RegisterUserRequest
import com.example.network.model.auth.TokenResponse

interface AuthDataSource {
    suspend fun loginKakao(loginKakaoRequest: LoginKakaoRequest) : Result<LoginKakaoResponse>
    suspend fun registerUser(registerUserRequest: RegisterUserRequest) : Result<TokenResponse>
}