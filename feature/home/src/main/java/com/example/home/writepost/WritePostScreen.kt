package com.example.home.writepost

import android.os.Trace
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
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


@Composable
internal fun WritePostRoute(
    navigateBack: () -> Unit,
    viewModel: WritePostViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is WritePostEvent.NavigateToBack -> navigateBack()
            }
        }
    }

    WritePostScreen(
        navigateBack = navigateBack
    )
}

@Composable
private fun WritePostScreen(
    navigateBack: () -> Unit,
) {
    val scrollState = rememberScrollState()
    var text by remember { mutableStateOf("") }
    var isTextVerified by remember { mutableStateOf(true) }
    var isImageVerified by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(vertical = 5.dp, horizontal = 15.dp)
                .align(Alignment.TopCenter), verticalAlignment = Alignment.CenterVertically
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Background)
                .verticalScroll(scrollState)
                .align(Alignment.Center)
                .padding(horizontal = 15.dp)
        ) {

            BasicTextField(
                value = "제목",
                onValueChange = {  },
                textStyle = TraceTheme.typography.bodyMSB,
                maxLines = 2,
                cursorBrush = SolidColor(PrimaryDefault),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(15.dp))

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Gray)
            )


        }


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(White)
                .padding(vertical = 5.dp, horizontal = 15.dp)
                .align(Alignment.BottomCenter), verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(com.example.designsystem.R.drawable.add_image_ic),
                contentDescription = "사진 첨부",
                tint = Black.copy(alpha = 0.7f),
                modifier = Modifier
                    .size(32.dp)
                    .clickable() {
                        navigateBack()
                    },
            )

            Spacer(Modifier.weight(1f))

            Row(
                modifier = Modifier.clickable(isRipple = true) {
                    isTextVerified = !isTextVerified
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
                    isTextVerified = !isTextVerified
                }
            ) {
                Image(
                    painter = if (isImageVerified) painterResource(R.drawable.checkbox_on) else painterResource(
                        R.drawable.checkbox_off
                    ),
                    contentDescription = "글 인증 여부",
                    modifier = Modifier
                        .size(20.dp)
                        .clickable(isRipple = true) {
                            isImageVerified = !isImageVerified
                        }
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
    WritePostScreen(navigateBack = {})
}