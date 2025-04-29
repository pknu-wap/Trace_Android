package com.example.mission.graph

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MissionViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<MissionEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()


    sealed class MissionEvent {

    }

}