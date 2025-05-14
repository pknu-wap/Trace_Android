package com.example.domain.repository

import com.example.domain.model.auth.User

interface AuthRepository {
    suspend fun loginKakao(idToken : String) : Result<User>
    suspend fun registerUser(signUpToken : String, providerId : String, nickName : String, profileImageUrl : String?) : Result<Unit>
//    suspend fun logOut() : Result<Unit>
//    suspend fun withdraw() : Result<Unit>
}