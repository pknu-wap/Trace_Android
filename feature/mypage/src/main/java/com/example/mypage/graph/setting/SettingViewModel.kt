package com.example.mypage.graph.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.EventHelper
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    val eventHelper: EventHelper,
) : ViewModel() {
    private val _eventChannel = Channel<SettingEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun logout() = viewModelScope.launch {
        authRepository.logOut().onSuccess {
            _eventChannel.send(SettingEvent.LogoutSuccess)
        }.onFailure {
            _eventChannel.send(SettingEvent.LogoutFailure)
        }
    }

    fun unRegisterUser() = viewModelScope.launch {
        authRepository.unRegisterUser().onSuccess {
            _eventChannel.send(SettingEvent.UnRegisterUserSuccess)
        }.onFailure {
            _eventChannel.send(SettingEvent.UnRegisterUserFailure)
        }
    }

    sealed class SettingEvent {
        data object NavigateBack : SettingEvent()
        data object LogoutSuccess : SettingEvent()
        data object LogoutFailure : SettingEvent()
        data object UnRegisterUserSuccess : SettingEvent()
        data object UnRegisterUserFailure : SettingEvent()
    }
}
