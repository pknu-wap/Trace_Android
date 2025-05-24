package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.model.post.PostFeed
import com.example.domain.model.post.TabType
import com.example.network.model.post.Cursor
import com.example.network.source.post.PostDataSource


class PostPagingSource(
    private val postDatasource: PostDataSource,
    private val tabType: TabType,
    private val pageSize: Int = 20
) : PagingSource<Cursor, PostFeed>() {

    override suspend fun load(params: LoadParams<Cursor>): LoadResult<Cursor, PostFeed> {
        return try {
            val cursor = params.key

            val response = postDatasource.getPosts(
                cursorDateTime = cursor?.dateTime,
                cursorId = cursor?.id,
                size = pageSize,
                postType = tabType
            ).getOrThrow()

            val postFeeds = response.toDomain()

            val nextCursor = if (response.hasNext) Cursor(
                id = response.cursor?.id
                    ?: throw IllegalStateException("Cursor must be present when hasNext is true"),
                dateTime = response.cursor?.dateTime
                    ?: throw IllegalStateException("Cursor must be present when hasNext is true")

            ) else null

            LoadResult.Page(
                data = postFeeds,
                prevKey = null,
                nextKey = nextCursor
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Cursor, PostFeed>): Cursor? = null
}
