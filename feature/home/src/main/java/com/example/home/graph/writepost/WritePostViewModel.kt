package com.example.home.graph.writepost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.EventHelper
import com.example.domain.model.post.WritePostType
import com.example.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WritePostViewModel @Inject constructor(
    private val postRepository: PostRepository,
    val eventHelper: EventHelper
) : ViewModel() {
    private val _eventChannel = Channel<WritePostEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val _type: MutableStateFlow<WritePostType> = MutableStateFlow(WritePostType.GOOD_DEED)
    val type = _type.asStateFlow()

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content = _content.asStateFlow()

    private val _images: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val images = _images.asStateFlow()

    private val _isVerified = MutableStateFlow(true)
    val isVerified = _isVerified.asStateFlow()

    private val _isCreatingPost = MutableStateFlow(false)
    val isCreatingPost = _isCreatingPost.asStateFlow()

    fun setType(type: WritePostType) {
        _type.value = type
    }

    fun setTitle(title: String) {
        _title.value = title
    }

    fun setContent(content: String) {
        _content.value = content
    }

    fun setIsVerified(isVerified: Boolean) {
        _isVerified.value = isVerified
    }

    fun addImages(images: List<String>) {
        _images.value += images
    }

    fun removeImage(image: String) {
        _images.value = _images.value.filter { it != image }
    }

    fun addPost() = viewModelScope.launch {
        _isCreatingPost.value = true

        postRepository.addPost(
            _type.value,
            _title.value,
            _content.value,
            _images.value
        ).onSuccess { postId ->
            _eventChannel.send(WritePostEvent.AddPostSuccess(postId = postId))
        }.onFailure {
            _eventChannel.send(WritePostEvent.AddPostFailure)
        }

        _isCreatingPost.value = false
    }

    sealed class WritePostEvent {
        data object NavigateToBack : WritePostEvent()
        data class AddPostSuccess(val postId: Int) : WritePostEvent()
        data object AddPostFailure : WritePostEvent()
    }
}