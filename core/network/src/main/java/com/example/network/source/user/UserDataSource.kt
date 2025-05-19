package com.example.network.source.user

import com.example.network.model.user.LoadUserInfoResponse

interface UserDataSource {
    suspend fun loadUserInfo() : Result<LoadUserInfoResponse>
}