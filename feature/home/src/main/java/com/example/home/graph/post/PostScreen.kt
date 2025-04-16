package com.example.home.graph.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White
import com.example.home.graph.post.component.TraceCommnetField


@Composable
internal fun PostRoute(
    navigateBack: () -> Unit,
    viewModel: PostViewModel = hiltViewModel()
) {
    val commentInput by viewModel.commentInput.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                else -> {}
            }
        }
    }

    PostScreen(
        navigateBack = navigateBack,
        commentInput = commentInput,
        onCommentInputChange = viewModel::setCommentInput
    )
}

@Composable
private fun PostScreen(
    navigateBack: () -> Unit,
    commentInput: String,
    onCommentInputChange: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .imePadding()
    ) {


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
                .padding(start = 8.dp, end = 8.dp, bottom = 6.dp)
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


@Preview
@Composable
fun PostScreenPreview() {
    PostScreen(
        navigateBack = {}, commentInput = "", onCommentInputChange = {}
    )
}
