package com.example.domain.repository

interface NotificationRepository {
    suspend fun updateDeviceToken(token : String) : Result<Unit>
}