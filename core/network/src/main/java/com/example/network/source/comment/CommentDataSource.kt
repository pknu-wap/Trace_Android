package com.example.network.source.comment

import com.example.network.model.comment.CommentResponse

interface CommentDataSource {
    suspend fun addComment(
        postId : Int, content : String,
    ) : Result<CommentResponse>

//    suspend fun addReplyToComment(
//        postId: Int, commentId : Int, content : String,
//    ) : Result<CommentResponse>
//
    suspend fun deleteComment(
        commentId: Int,
    ) : Result<Unit>
}