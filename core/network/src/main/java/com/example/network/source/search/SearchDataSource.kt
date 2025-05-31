package com.example.network.source.search

import com.example.domain.model.post.SearchType
import com.example.domain.model.post.TabType
import com.example.network.model.post.GetPostsResponse
import kotlinx.datetime.LocalDateTime

interface SearchDataSource {
    suspend fun searchPosts(
        cursorDateTime: LocalDateTime?,
        cursorId: Int?,
        size: Int,
        tabType: TabType,
        keyword: String,
        searchType: SearchType
    ): Result<GetPostsResponse>
}