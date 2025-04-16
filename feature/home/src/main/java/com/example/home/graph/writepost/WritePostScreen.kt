package com.example.home.graph.writepost

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.PrimaryActive
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White
import com.example.domain.model.home.PostType
import com.example.home.graph.writepost.WritePostViewModel.WritePostEvent
import com.example.home.graph.writepost.component.ImageContent
import com.example.home.graph.writepost.component.TraceContentField
import com.example.home.graph.writepost.component.TraceTitleField


@Composable
internal fun WritePostRoute(
    navigateBack: () -> Unit,
    viewModel: WritePostViewModel = hiltViewModel(),
) {
    val type by viewModel.type.collectAsStateWithLifecycle()
    val title by viewModel.title.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()
    val images by viewModel.images.collectAsStateWithLifecycle()
    val isVerified by viewModel.isVerified.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is WritePostEvent.NavigateToBack -> navigateBack()
            }
        }
    }

    WritePostScreen(
        type = type,
        title = title,
        content = content,
        images = images,
        isVerified = isVerified,
        navigateBack = navigateBack,
        onTitleChange = viewModel::setTitle,
        onContentChange = viewModel::setContent,
        addImage = viewModel::addImage,
        removeImage = viewModel::removeImage,
        onTypeChange = viewModel::setType,
        onIsVerifiedChange = viewModel::setIsVerified,

        )
}

@Composable
private fun WritePostScreen(
    type: PostType,
    title: String,
    content: String,
    images: List<Uri>,
    isVerified: Boolean,
    onTypeChange: (PostType) -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    addImage: (Uri) -> Unit,
    removeImage: (Uri) -> Unit,
    onIsVerifiedChange: (Boolean) -> Unit,
    navigateBack: () -> Unit,
) {
    val contentFieldFocusRequester = remember { FocusRequester() }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                addImage(uri)
            }
        }
    )

    val lazyListState = rememberLazyListState()

    val requestAvailable = title.isNotEmpty() && content.isNotEmpty() && type != PostType.None

    Box(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
    ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(vertical = 70.dp, horizontal = 15.dp)
                .imePadding(),
            state = lazyListState
        ) {

            item {
                TraceTitleField(
                    value = title,
                    onValueChange = onTitleChange,
                    onNext = { contentFieldFocusRequester.requestFocus() }
                )

                Spacer(Modifier.height(15.dp))

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Gray)
                )

                Spacer(Modifier.height(15.dp))

                if (images.isNotEmpty()) ImageContent(images, removeImage)

                TraceContentField(
                    value = content,
                    onValueChange = onContentChange,
                    lazyListState = lazyListState,
                    hint = if (type == PostType.GoodDeed) "따뜻한 흔적을 남겨보세요!" else "내용을 입력하세요.",
                    modifier = Modifier.focusRequester(contentFieldFocusRequester)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(50.dp)
                .padding(horizontal = 15.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = "뒤로 가기",
                modifier = Modifier
                    .size(32.dp)
                    .clickable(
                        isRipple = true
                    ) {
                        navigateBack()
                    }
            )

            Spacer(Modifier.width(30.dp))

            Text("글 쓰기", style = TraceTheme.typography.headingMB)

            Spacer(Modifier.weight(1f))

            Text(
                "완료",
                style = TraceTheme.typography.bodyMM,
                color = if (requestAvailable) PrimaryActive else Gray,
                modifier = Modifier.clickable(isRipple = true, enabled = requestAvailable) {

                }

            )

        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .height(50.dp)
                .padding(vertical = 5.dp, horizontal = 15.dp)
                .align(Alignment.BottomCenter), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.add_image_ic),
                contentDescription = "사진 첨부",
                tint = Black.copy(alpha = 0.85f),
                modifier = Modifier
                    .size(32.dp)
                    .clickable() {
                        launcher.launch("image/*")
                    },
            )

            Spacer(Modifier.weight(1f))

            if (true) {
                Row(
                    modifier = Modifier.clickable(isRipple = true) {
                        onIsVerifiedChange(!isVerified)
                    }
                ) {
                    Image(
                        painter = if (isVerified) painterResource(R.drawable.checkbox_on) else painterResource(
                            R.drawable.checkbox_off
                        ),
                        contentDescription = "선행 인증",
                        modifier = Modifier.size(20.dp)
                    )

                    Spacer(Modifier.width(2.dp))


                    Text(
                        "선행 인증",
                        color = if (isVerified) PrimaryActive else Gray,
                        style = TraceTheme.typography.bodySM,
                    )
                }
            }


        }
    }
}


@Preview
@Composable
fun WritePostScreenPreview() {
    WritePostScreen(
        type = PostType.None,
        title = "",
        content = "",
        isVerified = true,
        onTypeChange = {},
        onTitleChange = {},
        onContentChange = {},
        onIsVerifiedChange = {},
        navigateBack = {},
        images = emptyList(),
        addImage = {},
        removeImage = {}

    )
}