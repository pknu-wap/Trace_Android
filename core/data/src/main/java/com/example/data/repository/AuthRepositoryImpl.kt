package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.data.image.ImageResizer
import com.example.datastore.datasource.token.LocalTokenDataSource
import com.example.domain.model.auth.UserRole
import com.example.domain.repository.AuthRepository
import com.example.network.source.auth.AuthDataSource
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
    private val localTokenDataSource: LocalTokenDataSource,
    private val imageResizer : ImageResizer
) : AuthRepository {
    override suspend fun loginKakao(idToken: String): Result<UserRole> = suspendRunCatching {
        val response = authDataSource.loginKakao(idToken).getOrThrow()

        if (response.userId != null) {
            UserRole.NONE
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

                UserRole.USER
            }

        }
    }

    override suspend fun registerUser(
        idToken: String,
        nickName: String,
        profileImageUrl: String?
    ): Result<Unit> = suspendRunCatching {
        val uploadImageUrl = profileImageUrl?.let { imageResizer.resizeImage(profileImageUrl) }
        val response = authDataSource.registerUser(idToken, nickName, uploadImageUrl).getOrThrow()

        coroutineScope {
            val accessTokenJob = launch {
                response.accessToken.let { localTokenDataSource.setAccessToken(it) }
            }

            val refreshTokenJob = launch {
                response.refreshToken.let { localTokenDataSource.setRefreshToken(it) }
            }

            accessTokenJob.join()
            refreshTokenJob.join()
        }
    }


}