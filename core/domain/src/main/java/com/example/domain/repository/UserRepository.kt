package com.example.domain.repository

import com.example.domain.user.UserInfo

interface UserRepository {
    suspend fun checkTokenHealth() : Result<Unit>

    suspend fun loadUserInfo() : Result<UserInfo>
}