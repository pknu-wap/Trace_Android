package com.example.domain.repository

import com.example.domain.model.mission.DailyMission

interface MissionRepository {
    suspend fun getDailyMission(): Result<DailyMission>
    suspend fun changeDailyMission(): Result<DailyMission>
    suspend fun verifyDailyMission(
        title: String,
        content: String,
        images: List<String>?
    ): Result<Int>
}