package com.example.network.source.auth

interface AuthDataSource {
    suspend fun loginKakao() : Result<Unit>
    suspend fun registerUser() : Result<Unit>
}