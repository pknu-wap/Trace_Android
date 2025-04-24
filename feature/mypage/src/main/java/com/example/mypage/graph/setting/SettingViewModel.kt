package com.example.mypage.graph.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<SettingEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    sealed class SettingEvent {
        data object NavigateBack : SettingEvent()
    }
}
