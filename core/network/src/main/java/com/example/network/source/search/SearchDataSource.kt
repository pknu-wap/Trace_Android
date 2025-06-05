package com.example.network.source.search


import com.example.domain.model.search.SearchType
import com.example.domain.model.search.SearchTab
import com.example.network.model.post.GetPostsResponse
import kotlinx.datetime.LocalDateTime

interface SearchDataSource {
    suspend fun searchPosts(
        cursorDateTime: LocalDateTime?,
        cursorId: Int?,
        size: Int,
        tabType: SearchTab,
        keyword: String,
        searchType: SearchType
    ): Result<GetPostsResponse>
}