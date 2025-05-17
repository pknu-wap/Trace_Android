package com.example.domain.repository

import com.example.domain.model.post.Comment

interface CommentRepository {
    suspend fun addComment(postId: Int, content: String): Result<Comment>

//    suspend fun addReplyToComment(postId: Int, commentId : Int, content: String) : Result<Comment>

    suspend fun deleteComment(commentId : Int): Result<Unit>
}