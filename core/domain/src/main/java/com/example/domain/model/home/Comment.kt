package com.example.domain.model.home

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Comment(
    val nickName: String,
    val profileImageUrl : String? = null,
    val content : String,
    val createdAt : LocalDateTime,
) {
    fun getFormattedTime(): String {
        val now = LocalDateTime.now()
        val duration = Duration.between(createdAt, now)

        // 1일 이상일 경우
        if (duration.toDays() >= 1) {
            return if (duration.toDays() <= 7) {
                "${duration.toDays()}일"
            } else {
                val formatter = DateTimeFormatter.ofPattern("M/d")
                createdAt.format(formatter)
            }
        }

        // 1시간 이상일 경우
        if (duration.toHours() >= 1) {
            return "${duration.toHours()}시간"
        }

        // 1분 이상일 경우
        if (duration.toMinutes() >= 1) {
            return "${duration.toMinutes()}분"
        }

        // 1분 미만일 경우
        return "방금"
    }
}