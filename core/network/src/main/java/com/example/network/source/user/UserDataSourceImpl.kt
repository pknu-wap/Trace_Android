package com.example.network.source.user

import com.example.network.api.TraceApi
import com.example.network.model.user.LoadUserInfoResponse
import javax.inject.Inject

class UserDataSourceImpl @Inject constructor(
    private val traceApi: TraceApi,
) : UserDataSource {
    override suspend fun loadUserInfo(): Result<LoadUserInfoResponse> = traceApi.loadUserInfo()
}