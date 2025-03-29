package com.example.network.source.auth

interface AuthDataSource {
    suspend fun loginKakao() : Result<Unit>
}