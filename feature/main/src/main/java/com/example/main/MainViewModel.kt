package com.example.main

import androidx.lifecycle.ViewModel
import com.example.common.event.EventHelper
import com.example.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    val eventHelper: EventHelper,
    private val userRepository: UserRepository
) : ViewModel() {
    private val _eventChannel = Channel<MainEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    sealed class MainEvent {
        data object NavigateHome : MainEvent()
    }
}