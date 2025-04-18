package com.example.home.graph.writepost

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.home.WritePostType
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

    private val _type : MutableStateFlow<WritePostType> = MutableStateFlow(WritePostType.NONE)
    val type = _type.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _images : MutableStateFlow<List<Uri>> = MutableStateFlow(emptyList())
    val images = _images.asStateFlow()

    private val _isVerified = MutableStateFlow(true)
    val isVerified = _isVerified.asStateFlow()

    fun setTitle(title: String) {
        _title.value = title
    }

    /**
     * Updates the current post content with the provided text.
     *
     * @param content The new content for the post.
     */
    fun setContent(content: String) {
        _content.value = content
    }

    /**
     * Updates the current post type state.
     *
     * @param type The new post type to set.
     */
    fun setType(type: WritePostType) {
        _type.value = type
    }

    fun setIsVerified(isVerified: Boolean) {
        _isVerified.value = isVerified
    }

    fun addImage(image: Uri) {
        _images.value = _images.value + image
        Log.d("images size", images.value.size.toString() )
    }

    fun removeImage(image: Uri) {
        _images.value = _images.value.filter { it != image }
    }

    /**
     * Sends a one-time write post event to observers via the event channel.
     *
     * Launches a coroutine to deliver the specified event asynchronously.
     */
    internal fun onEvent(event: WritePostEvent) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    sealed class WritePostEvent {
        data object NavigateToBack : WritePostEvent()
    }
}