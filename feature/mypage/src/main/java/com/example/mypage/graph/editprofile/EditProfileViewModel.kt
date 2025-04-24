package com.example.mypage.graph.editprofile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<EditProfileEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    sealed class EditProfileEvent {
        data object NavigateBack : EditProfileEvent()
    }
}
