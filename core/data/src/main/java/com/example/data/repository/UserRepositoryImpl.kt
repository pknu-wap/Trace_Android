package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.datastore.datasource.token.LocalTokenDataSource
import com.example.domain.repository.UserRepository
import com.example.network.source.auth.AuthDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localTokenDataSource: LocalTokenDataSource,
    private val authDataSource: AuthDataSource
) : UserRepository {
    override suspend fun checkTokenHealth(): Result<Unit> = suspendRunCatching {
        val token = localTokenDataSource.accessToken.first()
        if (token.isEmpty()) return@suspendRunCatching

        authDataSource.checkTokenHealth(token)
    }
}