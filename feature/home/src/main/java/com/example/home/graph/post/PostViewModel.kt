package com.example.home.graph.post

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.common.event.EventHelper
import com.example.common.event.TraceEvent
import com.example.domain.model.post.Comment
import com.example.domain.model.post.EmotionCount
import com.example.domain.model.post.PostDetail
import com.example.domain.model.post.PostType
import com.example.domain.repository.CommentRepository
import com.example.domain.repository.PostRepository
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
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository,
    private val savedStateHandle: SavedStateHandle,
    val eventHelper: EventHelper
) : ViewModel() {
    private val _eventChannel = Channel<PostEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private val postId: Int = savedStateHandle["postId"] ?: 5

    init {
        getPost()
    }

    private val _postDetail = MutableStateFlow(fakePostDetail)
    val postDetail = _postDetail.asStateFlow()

    private val _commentInput = MutableStateFlow("")
    val commentInput = _commentInput.asStateFlow()

    private val _isCommentLoading = MutableStateFlow(false)
    val isCommentLoading = _isCommentLoading.asStateFlow()

    private val _replyTargetId: MutableStateFlow<Int?> = MutableStateFlow(null)
    val replyTargetId = _replyTargetId.asStateFlow()

    fun setCommentInput(commentInput: String) {
        _commentInput.value = commentInput
    }

    fun setReplyTargetId(commentId: Int) {
        _replyTargetId.value = commentId
    }

    fun clearReplyTargetId() {
        _replyTargetId.value = null
    }

    private fun getPost() = viewModelScope.launch {
        if (postId != 1) {
            postRepository.getPost(postId).onSuccess {
                _postDetail.value = it
                Log.d("postDetail", _postDetail.value.toString())
            }.onFailure {
                _postDetail.value = fakePostDetail
            }
        }
    }

    fun reportPost() {}

    fun deletePost() = viewModelScope.launch {
        postRepository.deletePost(postId = postId).onSuccess {
            _eventChannel.send(PostEvent.DeletePostSuccess)
        }.onFailure {
            _eventChannel.send(PostEvent.DeletePostFailure)
        }
    }

    fun addComment() = viewModelScope.launch {
        if (_commentInput.value.isEmpty()) {
            eventHelper.sendEvent(TraceEvent.ShowSnackBar("내용을 입력해주세요."))
            return@launch
        }

        _isCommentLoading.value = true

        commentRepository.addComment(postId = postId, content = _commentInput.value)
            .onSuccess { comment ->
                _postDetail.value = _postDetail.value.copy(
                    comments = _postDetail.value.comments + comment
                )

                _commentInput.value = ""
            }.onFailure {
                eventHelper.sendEvent(TraceEvent.ShowSnackBar("댓글 작성에 실패했습니다."))
            }

        _isCommentLoading.value = false

    }

    fun replyComment(): Int {
        var commentId = -1

        viewModelScope.launch {
            val parentId = _replyTargetId.value ?: return@launch

            if (_commentInput.value.isEmpty()) {
                eventHelper.sendEvent(TraceEvent.ShowSnackBar("내용을 입력해주세요."))
                return@launch
            }

            _isCommentLoading.value = true

            commentRepository.addReplyToComment(
                postId = postId,
                commentId = parentId,
                content = _commentInput.value
            ).onSuccess { replyComment ->
                val updatedComments = _postDetail.value.comments.map { comment ->
                    if (comment.commentId == parentId) {
                        comment.copy(replies = comment.replies + replyComment)
                    } else comment
                }

                _postDetail.value = _postDetail.value.copy(comments = updatedComments)

                clearReplyTargetId()
                _isCommentLoading.value = false
                _commentInput.value = ""

               commentId = replyComment.commentId
            }.onFailure {
                eventHelper.sendEvent(TraceEvent.ShowSnackBar("답글 작성에 실패했습니다."))
                _isCommentLoading.value = false
            }
        }

        Log.d("commentId", commentId.toString())
        return commentId
    }


    fun deleteComment(commentId: Int) = viewModelScope.launch {
        commentRepository.deleteComment(commentId).onSuccess {
            val updatedComments = _postDetail.value.comments.mapNotNull { comment ->
                when {
                    // 원댓글이 삭제 대상인 경우
                    comment.commentId == commentId -> {
                        if (comment.replies.isNotEmpty()) {
                            // 대댓글이 있으면 isDeleted만 true로 바꿈
                            comment.copy(isDeleted = true)
                        } else {
                            null
                        }
                    }

                    // 대댓글 중 삭제 대상이 있는 경우
                    comment.replies.any { it.commentId == commentId } -> {
                        val updatedReplies = comment.replies.mapNotNull { reply ->
                            if (reply.commentId == commentId) {
                                // 대댓글은 무조건 제거
                                null
                            } else reply
                        }
                        comment.copy(replies = updatedReplies)
                    }

                    // 나머지 댓글은 그대로
                    else -> comment
                }
            }

            _postDetail.value = _postDetail.value.copy(comments = updatedComments)
        }.onFailure {
            eventHelper.sendEvent(TraceEvent.ShowSnackBar("댓글 삭제에 실패했습니다."))
        }

    }

    fun reportComment(commentId: Int) {}

    sealed class PostEvent {
        data object DeletePostSuccess : PostEvent()
        data object DeletePostFailure : PostEvent()
    }

}

val fakeChildComments = listOf(
    Comment(
        nickName = "이수지",
        profileImageUrl = "https://randomuser.me/api/portraits/women/3.jpg",
        content = "정말 좋은 내용이에요!",
        createdAt = LocalDateTime.now().minusMinutes(30), providerId = "1234", postId = 1,
        commentId = 11, parentId = 1, isOwner = true,
    ),
    Comment(
        nickName = "박영희",
        profileImageUrl = null,
        content = "완전 공감해요!",
        createdAt = LocalDateTime.now().minusDays(2), providerId = "1234", postId = 1,
        commentId = 12, parentId = 1, isOwner = true,
    ),
    Comment(
        nickName = "최민준",
        profileImageUrl = null,
        content = "읽기만 했는데 좋네요!",
        createdAt = LocalDateTime.now().minusHours(10), providerId = "1234", postId = 1,
        commentId = 13, parentId = 1, isOwner = true,
    )
)

val fakeComments = listOf(
    Comment(
        nickName = "홍길동",
        profileImageUrl = "https://randomuser.me/api/portraits/men/1.jpg",
        content = "이 글 정말 감동적이에요!",
        createdAt = LocalDateTime.now().minusDays(1),
        providerId = "1234", postId = 1,
        commentId = 14, parentId = null, isOwner = true, replies = fakeChildComments
    ),
    Comment(
        nickName = "김민수",
        profileImageUrl = "https://randomuser.me/api/portraits/men/2.jpg",
        content = "좋은 글 감사합니다!",
        createdAt = LocalDateTime.now().minusHours(5), providerId = "1234", postId = 1,
        commentId = 24, parentId = null, isOwner = true,
    ),
    Comment(
        nickName = "이수지",
        profileImageUrl = "https://randomuser.me/api/portraits/women/3.jpg",
        content = "정말 좋은 내용이에요!",
        createdAt = LocalDateTime.now().minusMinutes(30), providerId = "1234", postId = 1,
        commentId = 34, parentId = null, isOwner = true,
    ),
    Comment(
        nickName = "박영희",
        profileImageUrl = null,
        content = "완전 공감해요!",
        createdAt = LocalDateTime.now().minusDays(2), providerId = "1234", postId = 1,
        commentId = 44, parentId = null, isOwner = true,
    ),
    Comment(
        nickName = "최민준",
        profileImageUrl = null,
        content = "읽기만 했는데 좋네요!",
        createdAt = LocalDateTime.now().minusHours(10), providerId = "1234", postId = 1,
        commentId = 54, parentId = null, isOwner = true,
    )
)

val fakePostDetail = PostDetail(
    postId = 0,
    providerId = "1234",
    postType = PostType.GOOD_DEED,
    title = "작은 선행을 나누다",
    content = "오늘은 작은 선행을 나누었습니다. 많은 사람들에게 도움이 되었으면 좋겠습니다.",
    nickname = "홍길동",
    viewCount = 120,
    comments = fakeComments,
    emotionCount = EmotionCount(
        heartWarmingCount = 35,
        likeableCount = 50,
        touchingCount = 15,
        impressiveCount = 20,
        gratefulCount = 10
    ),
    images = listOf(
        "https://picsum.photos/200/300?random=1",
        "https://picsum.photos/200/300?random=2",
        "https://picsum.photos/200/300?random=3"
    ),
    profileImageUrl = "https://picsum.photos/200/300?random=1",
    createdAt = LocalDateTime.now().minusDays(3),
    updatedAt = LocalDateTime.now(),
    isVerified = true,
    isOwner = true,

    )

