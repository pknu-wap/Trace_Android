package com.example.home.graph.post

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.GrayLine
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.WarmGray
import com.example.designsystem.theme.White
import com.example.domain.model.post.PostDetail
import com.example.domain.model.post.PostType
import com.example.home.graph.post.component.CommentView
import com.example.home.graph.post.component.OtherPostDropdownMenu
import com.example.home.graph.post.component.OwnPostDropdownMenu
import com.example.home.graph.post.component.PostImageContent
import com.example.home.graph.post.component.TraceCommentField


@Composable
internal fun PostRoute(
    navigateBack: () -> Unit,
    navigateToUpdatePost: (Int) -> Unit,
    viewModel: PostViewModel = hiltViewModel()
) {
    val commentInput by viewModel.commentInput.collectAsStateWithLifecycle()
    val postDetail by viewModel.postDetail.collectAsStateWithLifecycle()

    PostScreen(
        postDetail = postDetail,
        commentInput = commentInput,
        onCommentInputChange = viewModel::setCommentInput,
        onAddComment = {},
        onDeletePost = {},
        onReportPost = {},
        onDeleteComment = {},
        onReplyComment = {},
        onReportComment = {},
        navigateBack = navigateBack,
        navigateToUpdatePost = navigateToUpdatePost,
    )
}

@Composable
private fun PostScreen(
    postDetail: PostDetail,
    commentInput: String,
    onAddComment: () -> Unit,
    onCommentInputChange: (String) -> Unit,
    onDeletePost: (Int) -> Unit,
    onReportPost: (Int) -> Unit,
    onDeleteComment: (Int) -> Unit,
    onReplyComment: (Int) -> Unit,
    onReportComment: (Int) -> Unit,
    navigateToUpdatePost: (Int) -> Unit,
    navigateBack: () -> Unit,
) {
    var isOwnPostDropDownMenuExpanded by remember { mutableStateOf(false) }
    var isOtherPostDropDownMenuExpanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .imePadding()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp, start = 20.dp, end = 20.dp, bottom = 50.dp)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(postDetail.title, style = TraceTheme.typography.bodyLSB)

                    if (postDetail.postType == PostType.GOOD_DEED && postDetail.isVerified) {
                        Spacer(Modifier.width(8.dp))

                        Image(
                            painter = painterResource(R.drawable.verification_mark),
                            contentDescription = "선행 인증 마크",
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }


                Spacer(Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box() {
                        Canvas(modifier = Modifier.size(40.dp)) {
                            val canvasWidth = size.width
                            val center = center
                            val radius = canvasWidth / 2f

                            drawCircle(
                                color = PrimaryDefault,
                                radius = radius,
                                center = center,
                                style = Stroke(4f)
                            )
                        }

                        ProfileImage(postDetail.profileImageUrl)
                    }

                    Spacer(Modifier.width(10.dp))

                    Column(
                        modifier = Modifier.fillMaxHeight()
                    ) {
                        Text(postDetail.nickname, style = TraceTheme.typography.bodySSB)

                        Spacer(Modifier.height(3.dp))

                        Row() {
                            Text(
                                postDetail.getFormattedDate(),
                                style = TraceTheme.typography.bodyXSM,
                                color = DarkGray
                            )

                            Spacer(Modifier.width(10.dp))

                            Text(
                                "${postDetail.viewCount} 읽음",
                                style = TraceTheme.typography.bodyXSM,
                                color = DarkGray
                            )
                        }

                    }
                }

                Spacer(Modifier.height(10.dp))

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(GrayLine)
                )

                if (postDetail.images.isNotEmpty()) {
                    Spacer(Modifier.height(12.dp))

                    PostImageContent(images = postDetail.images)
                }

                Spacer(Modifier.height(15.dp))

                Text(postDetail.content, style = TraceTheme.typography.bodySR)

                Spacer(Modifier.height(50.dp))



                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(GrayLine)
                )

                if (postDetail.comments.isEmpty()) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(Modifier.height(55.dp))

                        Text(
                            "아직 댓글이 없습니다.",
                            style = TraceTheme.typography.bodyMM,
                            color = Black,
                        )

                        Spacer(Modifier.height(6.dp))

                        Text(
                            "당신의 생각을 댓글로 남겨주세요.",
                            style = TraceTheme.typography.bodySM,
                            color = WarmGray
                        )
                    }

                }

                postDetail.comments.forEachIndexed { index, comment ->
                    Spacer(Modifier.height(13.dp))

                    CommentView(comment = comment, onDelete = {
                        onDeleteComment(comment.commentId)
                    }, onReport = {
                        onReportComment(comment.commentId)
                    }, onReply = {
                        onReplyComment(comment.commentId)
                    })

                    if (index != postDetail.comments.size - 1) {
                        Spacer(Modifier.height(11.dp))

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(GrayLine)
                        )
                    }
                }

                Spacer(Modifier.height(100.dp))
            }
        }


        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .background(
                    PrimaryDefault
                )
                .padding(horizontal = 20.dp)
                .height(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.arrow_back_white_ic),
                contentDescription = "뒤로 가기",
                modifier = Modifier.clickable {
                    navigateBack()
                })

            Spacer(Modifier.width(20.dp))

            Text("흔적들", style = TraceTheme.typography.headingMB, color = White)

            Spacer(Modifier.weight(1f))

            Box() {
                Image(
                    painter = painterResource(R.drawable.menu_ic),
                    contentDescription = "메뉴",
                    modifier = Modifier.clickable(isRipple = true) {
                        if (postDetail.isOwner) {
                            isOwnPostDropDownMenuExpanded = true
                        } else {
                            isOtherPostDropDownMenuExpanded = true
                        }
                    })

                OwnPostDropdownMenu(
                    expanded = isOwnPostDropDownMenuExpanded,
                    onDismiss = { isOwnPostDropDownMenuExpanded = false },
                    onUpdate = { navigateToUpdatePost(postDetail.postId) },
                    onDelete = { onDeletePost(postDetail.postId) })

                OtherPostDropdownMenu(
                    expanded = isOtherPostDropDownMenuExpanded,
                    onDismiss = { isOtherPostDropDownMenuExpanded = false },
                    onReport = { onReportPost(postDetail.postId) })
            }
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 8.dp, end = 8.dp, bottom = 10.dp)
                .clip(RoundedCornerShape(15.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TraceCommentField(
                value = commentInput,
                onValueChange = onCommentInputChange,
                onAddComment = {},
            )
        }
    }
}

@Composable
private fun ProfileImage(imageUrl: String?) {
    val profileImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl ?: R.drawable.default_profile).crossfade(true).build()
    )
    val imageSize = if (imageUrl != null) 38.dp else 34.dp
    val paddingValue = if (imageUrl != null) 1.dp else 3.dp

    Box(Modifier.padding(paddingValue)) {
        Image(
            painter = profileImage,
            contentDescription = "프로필 이미지",
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}


@Preview
@Composable
fun PostScreenPreview() {
    PostScreen(
        commentInput = "",
        postDetail = fakePostDetail,
        onCommentInputChange = {},
        navigateBack = {},
        navigateToUpdatePost = {},
        onAddComment = {},
        onReplyComment = {},
        onDeleteComment = {},
        onDeletePost = {},
        onReportComment = {},
        onReportPost = {})
}
