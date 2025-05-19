package com.example.mypage.graph.setting

import androidx.lifecycle.ViewModel
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject


@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _eventChannel = Channel<SettingEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    fun logOut() {

    }

     fun unRegisterUser() {

    }

    sealed class SettingEvent {
        data object NavigateBack : SettingEvent()
        data object NavigateToLogin : SettingEvent()
    }
}
