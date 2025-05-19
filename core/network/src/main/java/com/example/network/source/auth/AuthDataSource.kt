package com.example.network.source.auth

import com.example.network.model.auth.LoginKakaoResponse
import com.example.network.model.auth.TokenResponse
import java.io.InputStream

interface AuthDataSource {
    suspend fun loginKakao(idToken: String): Result<LoginKakaoResponse>

    suspend fun registerUser(
        signUpToken : String,
        providerId : String,
        nickname: String,
        profileImage: InputStream? = null
    ): Result<TokenResponse>

    suspend fun checkTokenHealth(token : String) : Boolean

    suspend fun logout() : Result<Unit>

    suspend fun unregisterUser() : Result<Unit>
}