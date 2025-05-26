package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.model.post.Comment
import com.example.network.model.cursor.Cursor
import com.example.network.source.comment.CommentDataSource

class CommentPagingSource(
    private val commentDataSource: CommentDataSource,
    private val postId : Int,
    private val pageSize: Int = 30
) : PagingSource<Cursor, Comment>() {

    override suspend fun load(params: LoadParams<Cursor>): LoadResult<Cursor, Comment> {
        return try {
            val cursor = params.key

            val response = commentDataSource.getComments(
                postId = postId,
                cursorDateTime = cursor?.dateTime,
                cursorId = cursor?.id,
                size = pageSize,
            ).getOrThrow()

            val comments = response.toDomain()

            val nextCursor = if (response.hasNext) Cursor(
                id = response.cursor?.id
                    ?: throw IllegalStateException("Cursor must be present when hasNext is true"),
                dateTime = response.cursor?.dateTime
                    ?: throw IllegalStateException("Cursor must be present when hasNext is true")

            ) else null

            LoadResult.Page(
                data = comments,
                prevKey = null,
                nextKey = nextCursor
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Cursor, Comment>): Cursor? = null

}