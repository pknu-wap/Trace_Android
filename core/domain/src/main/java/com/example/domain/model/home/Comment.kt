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
    /**
     * Returns a human-readable string representing how much time has passed since the comment was created.
     *
     * The result is formatted in Korean as follows:
     * - If at least one day has passed but no more than seven days, returns the number of days followed by "일".
     * - If more than seven days have passed, returns the creation date in "M/d" format.
     * - If at least one hour but less than a day has passed, returns the number of hours followed by "시간".
     * - If at least one minute but less than an hour has passed, returns the number of minutes followed by "분".
     * - If less than a minute has passed, returns "방금" (just now).
     *
     * @return A string describing the elapsed time since creation in a user-friendly format.
     */
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