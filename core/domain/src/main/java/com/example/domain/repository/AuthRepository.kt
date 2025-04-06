package com.example.domain.repository

interface AuthRepository {
    suspend fun loginKakao(idToken : String) : Result<Unit>
    suspend fun registerUser() : Result<Unit>
//    suspend fun logOut() : Result<Unit>
//    suspend fun withdraw() : Result<Unit>
}