package com.example.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.EventHelper
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val eventHelper: EventHelper,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _eventChannel = Channel<MainEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _isAppReady = MutableStateFlow(false)
    val isAppReady: StateFlow<Boolean> = _isAppReady.asStateFlow()

    internal fun checkSession() = viewModelScope.launch {
        when {
            userRepository.checkSession() -> { _eventChannel.send(MainEvent.NavigateHome) }
            else -> { _eventChannel.send(MainEvent.NavigateHome) }
        }

    }

    fun onAppReady() {
        _isAppReady.value = true
    }

    sealed class MainEvent {
        data object NavigateHome : MainEvent()
    }

}