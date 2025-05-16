package com.example.domain.repository

interface UserRepository {
    suspend fun checkTokenHealth() : Result<Unit>
}