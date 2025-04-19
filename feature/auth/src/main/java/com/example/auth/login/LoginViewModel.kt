package com.example.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.EventHelper
import com.example.common.event.TraceEvent
import com.example.domain.model.auth.UserRole
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    internal val eventHelper: EventHelper
) : ViewModel() {
    private val _eventChannel = Channel<LoginEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    internal fun onEvent(event: LoginEvent) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    internal fun loginKakao(idToken: String) = viewModelScope.launch {
        authRepository.loginKakao(idToken).onSuccess { userRole ->
            Log.d("idToken", userRole.toString() + "흠 성공?" )
            if (userRole == UserRole.NONE) _eventChannel.send(LoginEvent.NavigateEditProfile)
            else _eventChannel.send(LoginEvent.NavigateToHome)
        }.onFailure {
            eventHelper.sendEvent(TraceEvent.ShowSnackBar("로그인에 실패했습니다"))
        }
    }

    sealed class LoginEvent {
        data object NavigateEditProfile : LoginEvent()
        data object NavigateToHome : LoginEvent()
    }

}