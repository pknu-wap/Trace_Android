package com.example.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.common.util.suspendRunCatching
import com.example.data.image.ImageResizer
import com.example.data.paging.MissionPagingSource
import com.example.domain.model.mission.DailyMission
import com.example.domain.model.mission.MissionFeed
import com.example.domain.repository.MissionRepository
import com.example.network.source.mission.MissionDataSource
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow


class MissionRepositoryImpl @Inject constructor(
    private val missionDataSource: MissionDataSource,
    private val imageResizer: ImageResizer
) : MissionRepository {
    override fun getCompletedMissions(): Flow<PagingData<MissionFeed>> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE),
            pagingSourceFactory = {
                MissionPagingSource(missionDataSource)
            }
        ).flow
    }

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

    companion object {
        private const val DEFAULT_PAGE_SIZE = 20
    }
}