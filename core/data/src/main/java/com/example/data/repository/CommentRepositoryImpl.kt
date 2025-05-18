package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.domain.model.post.Comment
import com.example.domain.repository.CommentRepository
import com.example.network.source.comment.CommentDataSource
import kotlinx.datetime.toJavaLocalDateTime
import javax.inject.Inject

class CommentRepositoryImpl @Inject constructor(
    private val commentDataSource: CommentDataSource,
) : CommentRepository {
    override suspend fun addComment(postId: Int, content: String): Result<Comment> =
        suspendRunCatching {
            val response =
                commentDataSource.addComment(postId = postId, content = content).getOrThrow()

            Comment(
                postId = response.postId,
                providerId = response.providerId,
                commentId = response.commentId,
                parentId = response.parentId,
                nickName = response.nickName,
                profileImageUrl = response.userProfileImageUrl,
                content = response.content,
                createdAt = response.createdAt.toJavaLocalDateTime(),
                isDeleted = response.isDeleted,
                isOwner = response.isOwner,
                replies = emptyList()
            )

        }

    override suspend fun addReplyToComment(
        postId: Int,
        commentId: Int,
        content: String
    ): Result<Comment> = suspendRunCatching {
        val response = commentDataSource.addReplyToComment(
            postId = postId,
            commentId = commentId,
            content = content,
        ).getOrThrow()

        Comment(
            postId = response.postId,
            providerId = response.providerId,
            commentId = response.commentId,
            parentId = response.parentId,
            nickName = response.nickName,
            profileImageUrl = response.userProfileImageUrl,
            content = response.content,
            createdAt = response.createdAt.toJavaLocalDateTime(),
            isDeleted = response.isDeleted,
            isOwner = response.isOwner,
            replies = emptyList()
        )
    }

    override suspend fun deleteComment(commentId: Int): Result<Unit> = suspendRunCatching {
        commentDataSource.deleteComment(commentId = commentId)
    }
}