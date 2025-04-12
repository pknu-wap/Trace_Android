package com.example.home.writepost

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.common.util.clickable
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.PrimaryActive
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White
import com.example.designsystem.R
import com.example.home.writepost.WritePostViewModel.WritePostEvent
import com.example.home.writepost.WritePostViewModel.PostType
import com.example.home.writepost.component.ImageContent
import com.example.home.writepost.component.TraceContentField
import com.example.home.writepost.component.TraceTitleField


@Composable
internal fun WritePostRoute(
    navigateBack: () -> Unit,
    viewModel: WritePostViewModel = hiltViewModel(),
) {
    val type by viewModel.type.collectAsStateWithLifecycle()
    val title by viewModel.title.collectAsStateWithLifecycle()
    val content by viewModel.content.collectAsStateWithLifecycle()
    val images by viewModel.images.collectAsStateWithLifecycle()
    val isTextVerified by viewModel.isTextVerified.collectAsStateWithLifecycle()
    val isImageVerified by viewModel.isImageVerified.collectAsStateWithLifecycle()

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
        isTextVerified = isTextVerified,
        isImageVerified = isImageVerified,
        navigateBack = navigateBack,
        onTitleChange = viewModel::setTitle,
        onContentChange = viewModel::setContent,
        addImage = viewModel::addImage,
        removeImage = viewModel::removeImage,
        onTypeChange = viewModel::setType,
        onTextVerifiedChange = viewModel::setIsTextVerified,
        onImageVerifiedChange = viewModel::setIsImageVerified
    )
}

@Composable
private fun WritePostScreen(
    type: PostType,
    title: String,
    content: String,
    images: List<Uri>,
    isTextVerified: Boolean,
    isImageVerified: Boolean,
    onTypeChange: (PostType) -> Unit,
    onTitleChange: (String) -> Unit,
    onContentChange: (String) -> Unit,
    addImage: (Uri) -> Unit,
    removeImage: (Uri) -> Unit,
    onTextVerifiedChange: (Boolean) -> Unit,
    onImageVerifiedChange: (Boolean) -> Unit,
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
        ) {

            item {
                TraceTitleField(
                    value = title,
                    onValueChange = onTitleChange,
                    hint = "제목",
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
                    hint = if (type == PostType.GoodDeed) "따뜻한 흔적을 남겨보세요!" else "내용을 입력하세요.",
                    modifier = Modifier.focusRequester(contentFieldFocusRequester)
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
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
                style = TraceTheme.typography.bodyMM, color = Gray
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

            Row(
                modifier = Modifier.clickable(isRipple = true) {
                    onTextVerifiedChange(!isTextVerified)
                }
            ) {
                Image(
                    painter = if (isTextVerified) painterResource(R.drawable.checkbox_on) else painterResource(
                        R.drawable.checkbox_off
                    ),
                    contentDescription = "글 인증 여부",
                    modifier = Modifier.size(20.dp)
                )

                Spacer(Modifier.width(2.dp))


                Text(
                    "글 인증",
                    color = if (isTextVerified) PrimaryActive else Gray,
                    style = TraceTheme.typography.bodySM,
                )
            }



            Spacer(Modifier.width(5.dp))

            Row(
                modifier = Modifier.clickable(isRipple = true) {
                    onImageVerifiedChange(!isImageVerified)
                }
            ) {
                Image(
                    painter = if (isImageVerified) painterResource(R.drawable.checkbox_on) else painterResource(
                        R.drawable.checkbox_off
                    ),
                    contentDescription = "사진 인증 여부",
                    modifier = Modifier
                        .size(20.dp)
                )

                Spacer(Modifier.width(2.dp))

                Text(
                    "사진 인증",
                    color = if (isImageVerified) PrimaryActive else Gray,
                    style = TraceTheme.typography.bodySM,
                )
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
        isTextVerified = true,
        isImageVerified = false,
        onTypeChange = {},
        onTitleChange = {},
        onContentChange = {},
        onTextVerifiedChange = {},
        onImageVerifiedChange = {},
        navigateBack = {},
        images = emptyList(),
        addImage = {},
        removeImage = {}

    )
}