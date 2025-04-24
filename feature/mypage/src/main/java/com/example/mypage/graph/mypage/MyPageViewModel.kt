package com.example.mypage.graph.mypage

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<MyPageEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    sealed class MyPageEvent {
        data object NavigateToEditProfile : MyPageEvent()
        data object NavigateToSetting : MyPageEvent()
    }
}
