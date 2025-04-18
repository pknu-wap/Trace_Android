package com.example.domain.model.home

import java.time.Duration
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
) {
     /**
     * Returns a human-readable string representing the elapsed time since the post was created.
     *
     * The format varies based on the time difference: days ago (up to 7 days), date ("M/d") for older posts, hours ago, minutes ago, or "방금 전" ("just now") for less than a minute.
     *
     * @return A formatted string indicating how much time has passed since creation.
     */
    fun getFormattedTime(): String {
        val now = LocalDateTime.now()
        val duration = Duration.between(createdAt, now)

        // 1일 이상일 경우
        if (duration.toDays() >= 1) {
            return if (duration.toDays() <= 7) {
                "${duration.toDays()}일 전"
            } else {
                val formatter = DateTimeFormatter.ofPattern("M/d")
                createdAt.format(formatter)
            }
        }

        // 1시간 이상일 경우
        if (duration.toHours() >= 1) {
            return "${duration.toHours()}시간 전"
        }

        // 1분 이상일 경우
        if (duration.toMinutes() >= 1) {
            return "${duration.toMinutes()}분 전"
        }

        // 1분 미만일 경우
        return "방금 전"
    }
}

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
    /**
     * Returns the creation timestamp formatted as "M/d HH:mm".
     *
     * @return The formatted creation date and time of the post.
     */
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