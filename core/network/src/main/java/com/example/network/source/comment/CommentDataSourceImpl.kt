package com.example.network.source.comment

import com.example.network.api.TraceApi
import com.example.network.model.comment.AddCommentRequest
import com.example.network.model.comment.CommentResponse
import javax.inject.Inject

class CommentDataSourceImpl @Inject constructor(
    private val traceApi: TraceApi,
) : CommentDataSource {
    override suspend fun addComment(postId: Int, content: String): Result<CommentResponse> =
        traceApi.addComment(
            postId = postId, addCommentRequest = AddCommentRequest(content)
        )

    override suspend fun deleteComment(commentId: Int): Result<Unit> =
        traceApi.deleteComment(commentId)
}