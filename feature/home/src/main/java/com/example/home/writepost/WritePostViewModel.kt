package com.example.home.writepost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<WritePostEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    internal fun onEvent(event: WritePostEvent) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    sealed class WritePostEvent {
        data object NavigateToBack : WritePostEvent()
    }
}