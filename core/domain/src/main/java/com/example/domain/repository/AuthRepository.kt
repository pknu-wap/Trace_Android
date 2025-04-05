package com.example.domain.repository

interface AuthRepository {
    suspend fun loginKakao(idToken : String) : Result<Unit>
    suspend fun signUp() : Result<Unit>
//    suspend fun logOut() : Result<Unit>
//    suspend fun withdraw() : Result<Unit>
}