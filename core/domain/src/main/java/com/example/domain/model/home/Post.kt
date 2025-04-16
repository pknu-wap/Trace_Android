package com.example.domain.model.home

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class PostFeed(
    val postType: PostType,
    val title: String,
    val content: String,
    val nickname: String,
    val createdAt : LocalDateTime,
    val viewCount: Int = 0,
    val commentCount: Int = 0,
    val isVerified : Boolean = false,
    val imageUri: String = "",
)

data class PostDetail(
    val postType : PostType,
    val title : String,
    val content : String,
    val nickname: String,
    val profileImageUrl : String? = null,
    val createdAt: LocalDateTime,
    val viewCount : Int,
    val comments: List<Comment>,
    val feelingCount : FeelingCount,
    val images : List<String> = emptyList(),
) {
    fun getFormattedDate(): String {
        val formatter = DateTimeFormatter.ofPattern("M/d HH:mm")
        return createdAt.format(formatter)
    }
}

enum class PostType(val label: String) {
    All("전체"),
    Free("자유"),
    GoodDeed("선행"),
    Mission("미션")
}

enum class WritePostType(val label : String) {
    GoodDeed("선행"),
    Free("자유"),
    NONE("없음")
}