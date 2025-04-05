package com.example.data.repository

import com.example.domain.repository.AuthRepository
import com.example.network.source.auth.AuthDataSource
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource : AuthDataSource
) : AuthRepository {
    override suspend fun loginKakao(idToken : String): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun signUp(): Result<Unit> {
        return Result.success(Unit)
    }
}