package com.example.data.repository

import com.example.datastore.datasource.token.LocalTokenDataSource
import com.example.domain.repository.UserRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val localTokenDataSource: LocalTokenDataSource
) : UserRepository {
    override suspend fun checkSession(): Boolean = localTokenDataSource.accessToken.first().isNotEmpty()
}