package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.data.image.ImageResizer
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
    private val imageResizer : ImageResizer
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

    override suspend fun registerUser(
       signUpToken: String, 
      providerId: String,
        nickName: String,
        profileImageUrl: String?
    ): Result<Unit> = suspendRunCatching {
        val uploadImageUrl = profileImageUrl?.let { imageResizer.resizeImage(profileImageUrl) }
        val response = authDataSource.registerUser(signUpToken, providerId, nickName, uploadImageUrl).getOrThrow()

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

    override suspend fun saveFake() = suspendRunCatching {
        coroutineScope {
            localTokenDataSource.setAccessToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI0MjM3NzkyNDE0Iiwicm9sZSI6IlJPTEVfVVNFUiIsImlhdCI6MTc0NzMwNjY1MSwiZXhwIjoxNzQ3MzEwMjUxfQ._-lEqLcOtEU2tKtSOTKUWO_ly9fdGyLf1mpuqqzviD_Nh4aipo5Knw9gQ84kMzI8_hV21yS5w2mDE4g6R9suGA")
        }
    }

    override suspend fun logOut(): Result<Unit> = suspendRunCatching {
        authDataSource.logout()
    }

    override suspend fun unRegisterUser(): Result<Unit> = suspendRunCatching {
       authDataSource.unRegisterUser()
    }
}