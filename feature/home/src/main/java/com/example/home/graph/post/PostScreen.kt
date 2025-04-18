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
import com.example.domain.model.home.PostDetail
import com.example.home.graph.post.PostViewModel.PostEvent
import com.example.home.graph.post.component.CommentView
import com.example.home.graph.post.component.OtherPostDropdownMenu
import com.example.home.graph.post.component.OwnPostDropdownMenu
import com.example.home.graph.post.component.PostImageContent
import com.example.home.graph.post.component.TraceCommnetField


/**
 * Composable entry point for the post detail screen, connecting UI state and events to the PostViewModel.
 *
 * Collects post and comment state from the view model, listens for navigation events, and displays the post detail UI.
 */
@Composable
internal fun PostRoute(
    navigateBack: () -> Unit,
    viewModel: PostViewModel = hiltViewModel()
) {
    val userId by viewModel.userId.collectAsStateWithLifecycle()
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
        userId = userId,
        postDetail = postDetail,
        commentInput = commentInput,
        onCommentInputChange = viewModel::setCommentInput
    )
}

/**
 * Displays the detailed post screen with post content, author information, images, and comments.
 *
 * Shows a top app bar with navigation and menu options, the post's details, a scrollable list of comments, and a comment input field at the bottom. Dropdown menus for editing, deleting, or reporting the post are conditionally shown based on the user ID.
 *
 * @param navigateBack Callback invoked when the back button is pressed.
 * @param userId The current user's ID; used to determine menu options.
 * @param postDetail The post data to display, including comments and images.
 * @param commentInput The current text in the comment input field.
 * @param onCommentInputChange Callback for updating the comment input text.
 */
@Composable
private fun PostScreen(
    navigateBack: () -> Unit,
    userId: String,
    postDetail: PostDetail,
    commentInput: String,
    onCommentInputChange: (String) -> Unit
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

                    CommentView(comment = comment)

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

            Box() {
                Image(
                    painter = painterResource(R.drawable.menu_ic),
                    contentDescription = "메뉴",
                    modifier = Modifier
                        .clickable(isRipple = true) {
                            if (userId.isEmpty()) {
                                isOwnPostDropDownMenuExpanded = true
                            } else {
                                isOtherPostDropDownMenuExpanded = true
                            }
                        }
                )

                OwnPostDropdownMenu(
                    expanded = isOwnPostDropDownMenuExpanded,
                    onDismiss = { isOwnPostDropDownMenuExpanded = false },
                    onEdit = {},
                    onDelete = {}
                )

                OtherPostDropdownMenu(
                    expanded = isOtherPostDropDownMenuExpanded,
                    onDismiss = { isOtherPostDropDownMenuExpanded = false },
                    onReport = {}
                )

            }

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

/**
 * Displays a circular profile image, using a default image if no URL is provided.
 *
 * The image is loaded asynchronously and sized with padding based on whether a custom image URL is given.
 *
 * @param imageUrl The URL of the profile image, or null to use the default profile image.
 */
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


/**
 * Displays a preview of the post detail screen with sample data for UI development.
 */
@Preview
@Composable
fun PostScreenPreview() {
    PostScreen(
        navigateBack = {},
        commentInput = "",
        postDetail = fakePostDetail,
        userId = "",
        onCommentInputChange = {}
    )
}
