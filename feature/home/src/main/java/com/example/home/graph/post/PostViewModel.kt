package com.example.home.graph.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.post.Comment
import com.example.domain.model.post.FeelingCount
import com.example.domain.model.post.PostDetail
import com.example.domain.model.post.PostType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<PostEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    internal fun onEvent(event: PostEvent) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    private val _userId = MutableStateFlow("")
    val userId = _userId.asStateFlow()

    private val _postDetail = MutableStateFlow(fakePostDetail)
    val postDetail = _postDetail.asStateFlow()

    private val _commentInput = MutableStateFlow("")
    val commentInput = _commentInput.asStateFlow()

    fun setCommentInput(commentInput: String) {
        _commentInput.value = commentInput
    }

    sealed class PostEvent {
        data object NavigateBack : PostEvent()
    }
}

val fakeComments = listOf(
    Comment(
        nickName = "홍길동",
        profileImageUrl = "https://randomuser.me/api/portraits/men/1.jpg",
        content = "이 글 정말 감동적이에요!",
        createdAt = LocalDateTime.now().minusDays(1)
    ),
    Comment(
        nickName = "김민수",
        profileImageUrl = "https://randomuser.me/api/portraits/men/2.jpg",
        content = "좋은 글 감사합니다!",
        createdAt = LocalDateTime.now().minusHours(5)
    ),
    Comment(
        nickName = "이수지",
        profileImageUrl = "https://randomuser.me/api/portraits/women/3.jpg",
        content = "정말 좋은 내용이에요!",
        createdAt = LocalDateTime.now().minusMinutes(30)
    ),
    Comment(
        nickName = "박영희",
        profileImageUrl = null,
        content = "완전 공감해요!",
        createdAt = LocalDateTime.now().minusDays(2)
    ),
    Comment(
        nickName = "최민준",
        profileImageUrl = null,
        content = "읽기만 했는데 좋네요!",
        createdAt = LocalDateTime.now().minusHours(10)
    )
)

val fakePostDetail = PostDetail(
    postType = PostType.GOOD_DEED,
    title = "작은 선행을 나누다",
    content = "오늘은 작은 선행을 나누었습니다. 많은 사람들에게 도움이 되었으면 좋겠습니다.",
    nickname = "홍길동",
    viewCount = 120,
    comments = fakeComments,
    feelingCount = FeelingCount(
        heartWarmingCount = 35,
        likeableCount = 50,
        touchingCount = 15,
        impressiveCount = 20,
        gratefulCount = 10
    ),
    profileImageUrl = "https://picsum.photos/200/300?random=1",
    createdAt = LocalDateTime.now().minusDays(3),
    isVerified = true,
    images = listOf(
        "https://picsum.photos/200/300?random=1",
        "https://picsum.photos/200/300?random=2",
        "https://picsum.photos/200/300?random=3"
    )
)

