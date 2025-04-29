package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.datastore.datasource.token.LocalTokenDataSource
import com.example.domain.model.auth.User
import com.example.domain.model.auth.UserRole
import com.example.domain.repository.AuthRepository
import com.example.network.source.auth.AuthDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
) : AuthRepository {
    override suspend fun loginKakao(idToken: String): Result<User> = suspendRunCatching {
        val response = authDataSource.loginKakao(idToken).getOrThrow()

        if (response.signupToken != null && response.providerId != null) {
            User(UserRole.NONE, response.signupToken, response.providerId)
        } else {
            coroutineScope {
                val accessTokenJob = launch {
                    response.accessToken?.let { localTokenDataSource.setAccessToken(it) }
                }

                val refreshTokenJob = launch {
                    response.refreshToken?.let { localTokenDataSource.setRefreshToken(it) }
                }

                accessTokenJob.join()
                refreshTokenJob.join()

                User(UserRole.USER)
            }

        }
    }


    override suspend fun registerUser(signUpToken: String, providerId: String): Result<Unit> {
        return Result.success(Unit)
    }
}