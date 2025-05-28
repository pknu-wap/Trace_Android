package com.example.network.source.notification

import android.util.Log
import com.example.network.api.TraceApi
import com.google.android.gms.tasks.Tasks
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NotificationDataSourceImpl @Inject constructor(
    private val traceApi: TraceApi,
    private val firebaseMessaging: FirebaseMessaging,
) : NotificationDataSource {
    override suspend fun updateDeviceToken(token: String): Result<Unit> {
       return Result.success(Unit)
    }

    override suspend fun postDeviceToken(): Result<Unit> {
        val token = getDeviceToken()
        Log.d("fcmToken", token)
        return Result.success(Unit)
    }

    override suspend fun getDeviceToken(): String = withContext(Dispatchers.IO) {
        Tasks.await(firebaseMessaging.token)
    }
}