package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.datastore.datasource.keyword.LocalKeywordDataSource
import com.example.domain.repository.SearchRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val localKeywordDataSource: LocalKeywordDataSource
) : SearchRepository {
    override suspend fun getRecentKeywords(): Result<List<String>> = suspendRunCatching {
       localKeywordDataSource.recentKeywords.firstOrNull() ?: emptyList()
    }

    override suspend fun addKeyword(keyword: String): Result<Unit> = suspendRunCatching {
        localKeywordDataSource.addKeyword(keyword)
    }

    override suspend fun removeKeyword(keyword: String): Result<Unit> = suspendRunCatching {
        localKeywordDataSource.removeKeyword(keyword)
    }

    override suspend fun clearKeywords(): Result<Unit> = suspendRunCatching {
        localKeywordDataSource.clearKeywords()
    }

}