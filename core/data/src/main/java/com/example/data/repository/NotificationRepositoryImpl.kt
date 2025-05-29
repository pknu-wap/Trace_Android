package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.domain.repository.NotificationRepository
import javax.inject.Inject

class NotificationRepositoryImpl @Inject constructor(

) : NotificationRepository {
    override suspend fun updateDeviceToken(token: String): Result<Unit> = suspendRunCatching {
        Result.success(Unit)
    }
}