package com.example.network.model.mission

import com.example.domain.model.mission.DailyMission
import com.example.domain.model.mission.Mission
import kotlinx.serialization.Serializable

@Serializable
data class DailyMissionResponse(
    val content: String,
    val changCount: Int,
) {
    fun toDomain(): DailyMission {
        return DailyMission(
            mission = Mission(content, false),
            changeCount = changCount
        )
    }
}
