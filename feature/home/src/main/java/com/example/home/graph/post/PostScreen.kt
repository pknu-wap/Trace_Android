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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.GrayLine
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White
import com.example.domain.model.home.PostDetail
import com.example.home.graph.post.PostViewModel.PostEvent
import com.example.home.graph.post.component.CommentView
import com.example.home.graph.post.component.PostImageContent
import com.example.home.graph.post.component.TraceCommnetField


@Composable
internal fun PostRoute(
    navigateBack: () -> Unit,
    viewModel: PostViewModel = hiltViewModel()
) {
    val commentInput by viewModel.commentInput.collectAsStateWithLifecycle()
    val postDetail by viewModel.postDetail.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                PostEvent.NavigateBack -> navigateBack()
            }
        }
    }

    PostScreen(
        navigateBack = { viewModel.onEvent(PostEvent.NavigateBack) },
        postDetail = postDetail,
        commentInput = commentInput,
        onCommentInputChange = viewModel::setCommentInput
    )
}

@Composable
private fun PostScreen(
    navigateBack: () -> Unit,
    postDetail: PostDetail,
    commentInput: String,
    onCommentInputChange: (String) -> Unit
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .imePadding()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 75.dp, start = 20.dp, end = 20.dp, bottom = 75.dp)
        ) {
            item {
                Text(postDetail.title, style = TraceTheme.typography.bodyLSB)

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
                            Text(postDetail.getFormattedDate(), style = TraceTheme.typography.bodyXSM, color = DarkGray)

                            Spacer(Modifier.width(10.dp))

                            Text("${postDetail.viewCount} 읽음", style = TraceTheme.typography.bodyXSM, color = DarkGray)
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

                if(postDetail.images.isNotEmpty()) {
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

                if(postDetail.comments.isEmpty()) Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("등록된 댓글이 없습니다.", style = TraceTheme.typography.bodyLM, color = Gray)
                }

                postDetail.comments.forEachIndexed { index, comment ->
                    Spacer(Modifier.height(13.dp))

                    CommentView(comment)

                    if(index != postDetail.comments.size - 1) {
                        Spacer(Modifier.height(11.dp))

                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(1.dp)
                                .background(GrayLine)
                        )
                    }
                }

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
                .size(50.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.arrow_back_white_ic),
                contentDescription = "뒤로 가기",
                modifier = Modifier
                    .clickable {
                        navigateBack()
                    }
            )

            Spacer(Modifier.width(20.dp))

            Text("흔적들", style = TraceTheme.typography.headingMB, color = White)

            Spacer(Modifier.weight(1f))

            Image(
                painter = painterResource(R.drawable.menu_ic),
                contentDescription = "메뉴",
                modifier = Modifier
                    .clickable(isRipple = true) {

                    }
            )
        }

        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 10.dp)
                .size(50.dp)
                .clip(RoundedCornerShape(15.dp)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TraceCommnetField(
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
            .data(imageUrl ?: R.drawable.default_profile)
            .crossfade(true)
            .build()
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
        navigateBack = {}, commentInput = "", postDetail = fakePostDetail, onCommentInputChange = {}
    )
}
