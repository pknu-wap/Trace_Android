package com.example.data.repository

import com.example.common.util.suspendRunCatching
import com.example.data.image.ImageResizer
import com.example.domain.model.mission.DailyMission
import com.example.domain.repository.MissionRepository
import com.example.network.source.mission.MissionDataSource
import jakarta.inject.Inject


class MissionRepositoryImpl @Inject constructor(
    private val missionDataSource: MissionDataSource,
    private val imageResizer: ImageResizer
) : MissionRepository {
    override suspend fun getDailyMission(): Result<DailyMission> = suspendRunCatching {
        missionDataSource.getDailyMission().getOrThrow().toDomain()
    }

    override suspend fun changeDailyMission(): Result<DailyMission> = suspendRunCatching {
        missionDataSource.changeDailyMission().getOrThrow().toDomain()
    }

    override suspend fun verifyDailyMission(
        title: String,
        content: String,
        images: List<String>?
    ): Result<Int> = suspendRunCatching {
        val imageStreams = images?.map { imageUrl ->
            imageResizer.resizeImage(imageUrl)
        }

        val response = missionDataSource.verifyDailyMission(
            title = title,
            content = content,
            images = imageStreams
        ).getOrThrow()

        response.id
    }
}