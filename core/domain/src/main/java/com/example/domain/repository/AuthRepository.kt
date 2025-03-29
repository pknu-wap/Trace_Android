package com.example.domain.repository

interface AuthRepository {
    suspend fun loginKakao() : Result<Unit>
//    suspend fun logOut() : Result<Unit>
//    suspend fun withdraw() : Result<Unit>
}