package com.example.network.model.mission

import com.example.domain.model.mission.MissionFeed
import com.example.network.model.cursor.Cursor
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toJavaLocalDate
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class GetCompletedMissionsResponse(
    val hasNext: Boolean,
    val cursor: Cursor?,
    val content: List<MissionContent>,
) {
    fun toDomain(): List<MissionFeed> = content.map { it.toDomain() }
}

@Serializable
data class MissionContent(
    val postId: Int,
    val content: String,
    val changeCount : Int,
    val isVerified: Boolean,
    val imageUrl: String? = null,
    val createdAt: LocalDate,
) {
    fun toDomain(): MissionFeed {
        return MissionFeed(
            missionId = postId,
            description = content,
            isVerified = isVerified,
            imageUrl = imageUrl,
            createdAt = createdAt.toJavaLocalDate()
        )
    }
}