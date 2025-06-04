package com.example.network.source.search

import com.example.domain.model.search.SearchTab
import com.example.domain.model.search.SearchType
import com.example.network.api.TraceApi
import com.example.network.model.post.GetPostsResponse
import com.example.network.model.search.SearchPostsRequest
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val traceApi: TraceApi
) : SearchDataSource {
    override suspend fun searchPosts(
        cursorDateTime: LocalDateTime?,
        cursorId: Int?,
        size: Int,
        tabType: SearchTab,
        keyword: String,
        searchType: SearchType
    ): Result<GetPostsResponse> = traceApi.searchPosts(
        searchPostsRequest = SearchPostsRequest(
            cursorDateTime = cursorDateTime,
            cursorId = cursorId,
            size = size,
            postType = if(tabType != SearchTab.ALL) tabType.name else null,
            keyword = keyword,
            searchType = searchType.name
        )
    )
}