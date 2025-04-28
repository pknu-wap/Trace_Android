package com.example.domain.model.mission

const val MAX_MISSION_CHANGE_COUNT = 10

data class DailyMission(
    val mission : String,
    val isVerficated : Boolean,
    val changeCount: Int = 0,
) {
    fun incrementChange(): DailyMission {
        val newCount = (changeCount + 1).coerceAtMost(MAX_MISSION_CHANGE_COUNT)
        return copy(changeCount = newCount)
    }
}
