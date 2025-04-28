package com.example.domain.model.mission

const val MAX_MISSION_CHANGE_COUNT = 10

data class DailyMission(
    val mission: Mission,
    val changeCount: Int = 0,
) {
    fun incrementChange(): DailyMission {
        val newCount = (changeCount + 1).coerceAtMost(MAX_MISSION_CHANGE_COUNT)
        return copy(changeCount = newCount)
    }
}

data class Mission(
    val description : String,
    val isVerficated : Boolean,
)
