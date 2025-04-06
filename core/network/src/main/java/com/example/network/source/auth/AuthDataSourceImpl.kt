package com.example.network.source.auth

import javax.inject.Singleton

@Singleton
class AuthDataSourceImpl : AuthDataSource {
    override suspend fun loginKakao(): Result<Unit> {
        return Result.success(Unit)
    }

    override suspend fun registerUser(): Result<Unit> {
        return Result.success(Unit)
    }

}