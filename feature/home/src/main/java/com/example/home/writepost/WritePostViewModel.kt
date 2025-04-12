package com.example.home.writepost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<WritePostEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _type : MutableStateFlow<PostType> = MutableStateFlow(PostType.None)
    val type = _type.asStateFlow()

    private val _isTextVerified = MutableStateFlow(true)
    val isTextVerified = _isTextVerified.asStateFlow()

    private val _isImageVerfied = MutableStateFlow(false)
    val isImageVerified = _isImageVerfied.asStateFlow()

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setContent(content: String) {
        _content.value = content
    }

    fun setType(type: PostType) {
        _type.value = type
    }

    fun setIsTextVerified(isVerified: Boolean) {
        _isTextVerified.value = isVerified
    }

    fun setIsImageVerified(isVerified: Boolean) {
        _isImageVerfied.value = isVerified
    }

    internal fun onEvent(event: WritePostEvent) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    sealed class PostType {
        object Free : PostType()
        object GoodDeed : PostType()
        object None : PostType()
    }

    sealed class WritePostEvent {
        data object NavigateToBack : WritePostEvent()
    }
}