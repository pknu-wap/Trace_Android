package com.example.domain.repository

interface UserRepository {
    suspend fun checkSession() : Boolean
}