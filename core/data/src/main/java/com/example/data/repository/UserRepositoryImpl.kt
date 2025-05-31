package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.data.image.ImageResizer
import com.example.datastore.datasource.token.LocalTokenDataSource
import com.example.datastore.datasource.user.LocalUserDataSource
import com.example.domain.model.user.UserInfo
import com.example.domain.repository.UserRepository
import com.example.network.source.auth.AuthDataSource
import com.example.network.source.user.UserDataSource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localTokenDataSource: LocalTokenDataSource,
    private val authDataSource: AuthDataSource,
    private val userDataSource: UserDataSource,
    private val localUserDataSource: LocalUserDataSource,
    private val imageResizer: ImageResizer,
) : UserRepository {
    override suspend fun checkTokenHealth(): Result<Unit> = suspendRunCatching {
        val token = localTokenDataSource.accessToken.first()
        if (token.isEmpty()) return@suspendRunCatching

        authDataSource.checkTokenHealth(token)
    }

    override suspend fun getUserInfo(): Result<UserInfo> = suspendRunCatching {
        val userInfo = localUserDataSource.userInfo.firstOrNull()
        userInfo ?: loadUserInfo().getOrThrow()
    }

    override suspend fun loadUserInfo(): Result<UserInfo> = suspendRunCatching {
        val response = userDataSource.loadUserInfo().getOrThrow()
        val userInfo = UserInfo(
            name = response.nickname,
            profileImageUrl = response.profileImageUrl,
            verificationScore = response.verificationScore,
            verificationCount = response.verificationCount,
        )

        localUserDataSource.setUserInfo(userInfo)
        userInfo
    }

    override suspend fun updateNickname(nickname: String): Result<Unit> = suspendRunCatching {
        userDataSource.updateNickname(nickname)
    }

    override suspend fun updateProfileImage(profileImageUrl: String?): Result<Unit> =
        suspendRunCatching {
            val uploadImageUrl = profileImageUrl?.let { imageResizer.resizeImage(profileImageUrl) }
            userDataSource.updateProfileImage(uploadImageUrl)
        }
}