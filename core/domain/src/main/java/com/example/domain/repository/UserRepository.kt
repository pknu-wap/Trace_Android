package com.example.domain.repository

import com.example.domain.user.UserInfo

interface UserRepository {
    suspend fun checkTokenHealth() : Result<Unit>
    suspend fun getUserInfo() : Result<UserInfo>
    suspend fun loadUserInfo() : Result<UserInfo>
}