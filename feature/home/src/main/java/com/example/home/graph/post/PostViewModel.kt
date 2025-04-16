package com.example.home.graph.post

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<PostEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _commentInput = MutableStateFlow("")
    val commentInput = _commentInput.asStateFlow()

    fun setCommentInput(commentInput: String) {
        _commentInput.value = commentInput
    }

    sealed class PostEvent {

    }

}