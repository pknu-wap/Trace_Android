package com.example.network.source.auth

import com.example.network.api.TraceApi
import com.example.network.model.auth.LoginKakaoRequest
import com.example.network.model.auth.LoginKakaoResponse
import com.example.network.model.auth.RegisterUserRequest
import com.example.network.model.auth.TokenResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthDataSourceImpl @Inject constructor(
    private val traceApi: TraceApi
) : AuthDataSource {
    override suspend fun loginKakao(loginKakaoRequest: LoginKakaoRequest): Result<LoginKakaoResponse> =
        traceApi.loginKakao(loginKakaoRequest = loginKakaoRequest)

    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): Result<TokenResponse> =
        traceApi.registerUser(registerUserRequest = registerUserRequest)
}