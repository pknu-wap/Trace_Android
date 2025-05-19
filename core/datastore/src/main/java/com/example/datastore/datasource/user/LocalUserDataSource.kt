package com.example.datastore.datasource.user

import com.example.domain.user.UserInfo
import kotlinx.coroutines.flow.Flow

interface LocalUserDataSource {
    val userInfo : Flow<UserInfo?>
    suspend fun setUserInfo(userInfo: UserInfo)
    suspend fun clearUserInfo()
}