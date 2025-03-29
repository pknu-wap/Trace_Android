package com.example.data.repository

import com.example.domain.repository.AuthRepository
import com.example.network.source.auth.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource : AuthDataSource
) : AuthRepository {
    override suspend fun loginKakao(): Result<Unit> {
        return Result.success(Unit)
    }
}