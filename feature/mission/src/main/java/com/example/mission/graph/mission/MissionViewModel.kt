package com.example.mission.graph.mission

import androidx.lifecycle.ViewModel
import com.example.domain.model.mission.DailyMission
import com.example.domain.model.mission.Mission
import com.example.domain.model.mission.MissionFeed
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<MissionEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _dailyMission = MutableStateFlow(fakeDailyMission)
    val dailyMission = _dailyMission.asStateFlow()

    private val _verifiedMissions = MutableStateFlow(fakeMissionFeeds1)
    val verifiedMissions = _verifiedMissions.asStateFlow()

    private val _unVerifiedMissions = MutableStateFlow(fakeMissionFeeds2)
    val unVerifiedMissions = _unVerifiedMissions.asStateFlow()

    fun changeMission() {
        _dailyMission.value = _dailyMission.value.incrementChange()
    }

    sealed class MissionEvent {
        data object SubmitMission : MissionEvent()
    }
}

private val fakeDailyMission = DailyMission(
    mission = Mission("길거리에서 쓰레기 줍기", isVerified = false),
    changeCount = 0
)

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

val fakeMissionFeeds2 = listOf(
    MissionFeed(
        missionId = 6,
        description = "지하철에서 어르신에게 자리 양보하기",
        isVerified = true,
        imageUrl = "https://picsum.photos/200/300?random=1",
        createdAt = LocalDateTime.of(2025, 5, 22, 9, 0)
    ),
    MissionFeed(
        missionId = 7,
        description = "길거리 쓰레기 줍기",
        isVerified = false,
        imageUrl = null,
        createdAt = LocalDateTime.of(2025, 5, 21, 16, 30)
    ),
    MissionFeed(
        missionId = 8,
        description = "카페에서 다 쓴 컵 정리하기",
        isVerified = true,
        imageUrl = "https://picsum.photos/200/300?random=6",
        createdAt = LocalDateTime.of(2025, 5, 20, 13, 15)
    ),
    MissionFeed(
        missionId = 9,
        description = "지인에게 따뜻한 말 한마디 전하기",
        isVerified = false,
        imageUrl = null,
        createdAt = LocalDateTime.of(2025, 5, 19, 10, 45)
    ),
    MissionFeed(
        missionId = 10,
        description = "엘리베이터 버튼 대신 눌러주기",
        isVerified = true,
        imageUrl = "https://picsum.photos/200/300?random=2",
        createdAt = LocalDateTime.of(2025, 5, 18, 17, 5)
    )
)


