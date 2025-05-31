package com.example.mission.graph.mission

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.EventHelper
import com.example.common.event.TraceEvent
import com.example.domain.model.mission.DailyMission
import com.example.domain.model.mission.Mission
import com.example.domain.model.mission.MissionFeed
import com.example.domain.repository.MissionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(
    private val missionRepository: MissionRepository,
    val eventHelper: EventHelper,
) : ViewModel() {
    private val _dailyMission = MutableStateFlow(
        DailyMission(
            mission = Mission("", isVerified = false),
            changeCount = 0
        )
    )
    val dailyMission = _dailyMission.asStateFlow()

    private val _verifiedMissions = MutableStateFlow(fakeMissionFeeds1)
    val verifiedMissions = _verifiedMissions.asStateFlow()

    init {
        getDailyMission()
    }

    private fun getDailyMission() = viewModelScope.launch {
        missionRepository.getDailyMission().onSuccess { dailyMission ->
            _dailyMission.value = dailyMission
        }
    }

    fun changeDailyMission() = viewModelScope.launch {
        missionRepository.changeDailyMission().onSuccess { dailyMission ->
            _dailyMission.value = dailyMission
        }.onFailure {
            eventHelper.sendEvent(TraceEvent.ShowSnackBar("일일 미션 변경횟수를 초과했습니다."))
        }
    }
}

val fakeMissionFeeds1 = listOf(
    MissionFeed(
        missionId = 1,
        description = "지하철에서 어르신에게 자리 양보하기",
        isVerified = true,
        imageUrl = "https://picsum.photos/200/300?random=1",
        createdAt = LocalDateTime.of(2025, 5, 22, 9, 0)
    ),
    MissionFeed(
        missionId = 2,
        description = "길거리 쓰레기 줍기",
        isVerified = false,
        imageUrl = null,
        createdAt = LocalDateTime.of(2025, 5, 21, 16, 30)
    ),
    MissionFeed(
        missionId = 3,
        description = "카페에서 다 쓴 컵 정리하기",
        isVerified = true,
        imageUrl = "https://picsum.photos/200/300?random=6",
        createdAt = LocalDateTime.of(2025, 5, 20, 13, 15)
    ),
    MissionFeed(
        missionId = 4,
        description = "지인에게 따뜻한 말 한마디 전하기",
        isVerified = false,
        imageUrl = null,
        createdAt = LocalDateTime.of(2025, 5, 19, 10, 45)
    ),
    MissionFeed(
        missionId = 5,
        description = "엘리베이터 버튼 대신 눌러주기",
        isVerified = true,
        imageUrl = "https://picsum.photos/200/300?random=2",
        createdAt = LocalDateTime.of(2025, 5, 18, 17, 5)
    )
)

