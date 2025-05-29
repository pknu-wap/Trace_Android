package com.example.network.source.mission

import com.example.network.model.mission.DailyMissionResponse
import com.example.network.model.post.PostResponse
import java.io.InputStream

interface MissionDataSource {
    suspend fun getDailyMission(): Result<DailyMissionResponse>
    suspend fun changeDailyMission(): Result<DailyMissionResponse>
    suspend fun verifyDailyMission(
        title: String,
        content: String,
        images: List<InputStream>?
    ): Result<PostResponse>
}