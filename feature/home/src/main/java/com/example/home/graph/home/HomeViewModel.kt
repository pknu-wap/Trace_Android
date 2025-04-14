package com.example.home.graph.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<HomeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    internal fun onEvent(event: HomeEvent) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    sealed class HomeEvent {
        data object NavigateToPost : HomeEvent()
        data object NavigateToWritePost : HomeEvent()
    }
}