package com.example.network.source.auth

import com.example.network.model.auth.LoginKakaoResponse

interface AuthDataSource {
    suspend fun loginKakao(idToken: String): Result<LoginKakaoResponse>

//    suspend fun registerUser(
//        idToken: String,
//        nickname: String,
//        profileImageUrl: String? = null
//    ): Result<TokenResponse>
}