package com.example.mission.graph.mission

import androidx.lifecycle.ViewModel
import com.example.domain.model.mission.DailyMission
import com.example.domain.model.mission.Mission
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<MissionEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _dailyMission = MutableStateFlow(fakeDailyMission)
    val dailyMission = _dailyMission.asStateFlow()

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