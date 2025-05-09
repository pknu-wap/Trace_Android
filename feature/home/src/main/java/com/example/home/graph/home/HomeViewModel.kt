package com.example.home.graph.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.post.PostFeed
import com.example.domain.model.post.PostType
import com.example.domain.model.post.TabType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(

) : ViewModel() {
    private val _eventChannel = Channel<HomeEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    internal fun onEvent(event: HomeEvent) = viewModelScope.launch {
        _eventChannel.send(event)
    }

    private val _postFeeds: MutableStateFlow<List<PostFeed>> = MutableStateFlow(fakePostFeeds)
    val postFeeds = _postFeeds.asStateFlow()

    private val _tabType : MutableStateFlow<TabType> = MutableStateFlow(TabType.ALL)
    val tabType = _tabType.asStateFlow()

    private fun setPostFeeds(postFeeds: List<PostFeed>) {
        _postFeeds.value = postFeeds
    }

    fun setTabType(tabType: TabType) {
        _tabType.value = tabType
    }

    sealed class HomeEvent {
        data class NavigateToPost(val postId : Int) : HomeEvent()
        data object NavigateToWritePost : HomeEvent()
    }
}

val fakePostFeeds: List<PostFeed> = listOf(
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "깨끗한 공원 만들기",
        content = "오늘 공원에서 쓰레기를 줍고 깨끗한 환경을 만들었습니다. 주변 사람들이 함께 참여해주셨습니다.",
        nickname = "선행자1",
        createdAt = LocalDateTime.now(),
        viewCount = 150,
        commentCount = 5,
        isVerified = true,
    ),
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "무료 식사 제공",
        content = "어려운 이웃을 위해 무료로 식사를 제공했습니다. 작은 도움이지만 큰 의미가 있었습니다.",
        nickname = "선행자2",
        createdAt = LocalDateTime.now().minusMinutes(30),
        viewCount = 220,
        commentCount = 10,
        isVerified = false,
        imageUri = "https://picsum.photos/200/300?random=2"
    ),
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "헌혈 참여",
        content = "지역 헌혈 행사에 참여하여 기부하였습니다. 많은 분들이 참여해주셔서 좋았습니다.",
        nickname = "선행자3",
        createdAt = LocalDateTime.now().minusMinutes(50),
        viewCount = 300,
        commentCount = 8,
        isVerified = true,
    ),
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "무료 도서 기부",
        content = "사용하지 않는 책을 기부하여 많은 사람들이 혜택을 볼 수 있게 했습니다.",
        nickname = "선행자4",
        createdAt = LocalDateTime.now().minusHours(1),
        viewCount = 175,
        commentCount = 12,
        isVerified = true,
        imageUri = "https://picsum.photos/200/300?random=4"
    ),
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "환경 보호 캠페인",
        content = "자연을 보호하는 캠페인에 참여했습니다. 지구를 위한 작은 노력!",
        nickname = "선행자5",
        createdAt = LocalDateTime.now().minusHours(3),
        viewCount = 500,
        commentCount = 35,
        isVerified = false,
    ),
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "기부금 모금 활동",
        content = "소외된 이웃을 돕기 위해 기부금을 모금하였습니다.",
        nickname = "선행자6",
        createdAt = LocalDateTime.now().minusDays(6),
        viewCount = 400,
        commentCount = 28,
        isVerified = true,
        imageUri = "https://picsum.photos/200/300?random=6"
    ),
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "청소년 멘토링 활동",
        content = "청소년들에게 멘토링을 통해 더 나은 미래를 꿈꾸도록 도왔습니다.",
        nickname = "선행자7",
        createdAt = LocalDateTime.now().minusDays(3),
        viewCount = 320,
        commentCount = 15,
        isVerified = true,
    ),
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "재활용 캠페인",
        content = "재활용을 촉진하는 캠페인에 참여해 재활용 활동을 지원했습니다.",
        nickname = "선행자8",
        createdAt = LocalDateTime.now().minusDays(4),
        viewCount = 220,
        commentCount = 18,
        isVerified = false,
        imageUri = "https://picsum.photos/200/300?random=8"
    ),
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "노숙인들에게 의류 기부",
        content = "기부한 옷이 많은 노숙인들에게 도움이 되었길 바랍니다.",
        nickname = "선행자9",
        createdAt = LocalDateTime.now().minusDays(6),
        viewCount = 250,
        commentCount = 13,
        isVerified = false,
        imageUri = "https://picsum.photos/200/300?random=9"
    ),
    PostFeed(
        postType = PostType.GOOD_DEED,
        title = "아름다운 거리 만들기",
        content = "동네에서 거리 청소와 아름다운 꽃밭을 조성했습니다.",
        nickname = "선행자10",
        createdAt = LocalDateTime.now().minusDays(8),
        viewCount = 100,
        commentCount = 5,
        isVerified = true,
        imageUri = "https://picsum.photos/200/300?random=10"
    )
)

