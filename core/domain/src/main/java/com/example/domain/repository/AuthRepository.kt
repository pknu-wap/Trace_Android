package com.example.domain.repository

import com.example.domain.model.auth.UserRole

interface AuthRepository {
    suspend fun loginKakao(idToken : String) : Result<UserRole>
    suspend fun registerUser(idToken: String) : Result<Unit>
//    suspend fun logOut() : Result<Unit>
//    suspend fun withdraw() : Result<Unit>
}