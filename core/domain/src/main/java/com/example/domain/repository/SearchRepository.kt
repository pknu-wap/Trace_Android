package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.model.post.PostFeed
import com.example.domain.model.search.SearchType
import com.example.domain.model.search.SearchTab
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchPosts(keyword: String, tabType : SearchTab, searchType: SearchType) : Flow<PagingData<PostFeed>>
    suspend fun getRecentKeywords(): Result<List<String>>
    suspend fun addKeyword(keyword : String) : Result<Unit>
    suspend fun removeKeyword(keyword: String) : Result<Unit>
    suspend fun clearKeywords() : Result<Unit>
}