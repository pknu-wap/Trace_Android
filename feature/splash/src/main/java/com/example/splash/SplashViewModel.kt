package com.example.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val userRepository: UserRepository,
) : ViewModel() {
    private val _eventChannel = Channel<SplashEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    init {
        checkSession()
    }

    private fun checkSession() = viewModelScope.launch {
        if (userRepository.checkSession()) _eventChannel.send(SplashEvent.NavigateToHome)
        else _eventChannel.send(SplashEvent.NavigateToLogin)
    }

    sealed class SplashEvent {
        data object NavigateToHome : SplashEvent()
        data object NavigateToLogin : SplashEvent()
    }
}
