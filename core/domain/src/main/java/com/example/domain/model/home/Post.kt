package com.example.domain.model.home

import java.time.LocalDateTime

data class PostFeed(
    val postType: PostType = PostType.None,
    val title: String,
    val content: String,
    val nickname: String,
    val createdAt : LocalDateTime,
    val viewCount: Int = 0,
    val commentCount: Int = 0,
    val isVerified : Boolean = false,
    val imageUri: String = "",
)

//data class PostDetail(
//    val postType : PostType,
//    val title : String,
//    val content : String,
//    val nickname: String,
//    val viewCount : Int,
//   val comments: List<Comment>,
//     val feelings : List<Feeling>
//    val images : List<String>,
//)


sealed class PostType {
    object Free : PostType()
    object GoodDeed : PostType()
    object Mission : PostType()
    object None : PostType()
}