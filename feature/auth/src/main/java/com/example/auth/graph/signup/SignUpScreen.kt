package com.example.auth.graph.signup

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.InputTransformation.Companion.keyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import com.example.auth.graph.signup.SignUpViewModel.SignUpEvent
import com.example.designsystem.R
import com.example.designsystem.theme.background
import com.example.designsystem.theme.field
import com.example.designsystem.theme.primaryActive
import com.example.designsystem.theme.primaryDefault
import com.example.designsystem.theme.primaryDefault85
import com.google.common.io.Files.append


@Composable
internal fun SignUpRoute(
    navigateToHome: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SignUpEvent.SignUpSuccess -> {
                    navigateToHome()
                }
            }
        }
    }

    SignUpScreen(
        viewModel::signUp
    )

}

@Composable
private fun SignUpScreen(
    signUp: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val signUpAvailability = true
        val keyboardController = LocalSoftwareKeyboardController.current


        Spacer(Modifier.height(57.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = primaryDefault)) {
                    append("프로필")
                }
                append(" 설정")
            }, style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp)
        )

        Spacer(Modifier.height(26.dp))

        Box() {
            CircleWithStroke()

            Image(
                painter = painterResource(id = R.drawable.default_profile),
                contentDescription = "기본 프로필 이미지",
                modifier = Modifier.padding(9.dp)
            )

            Box(
                modifier = Modifier.align(Alignment.BottomEnd)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.camera_ic),
                    contentDescription = "갤러리 불러오기",
                    modifier = Modifier.padding(12.dp)
                )
            }

        }

        Spacer(Modifier.height(46.dp))

        Text(
            "사용자 이름", style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
            color = primaryDefault,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp)
        )

        Spacer(Modifier.height(12.dp))

        var textState = remember { mutableStateOf(TextFieldValue()) }

        OutlinedTextField(
            value = textState.value,
            onValueChange = { textState.value = it },
            placeholder = {
                Text(
                    "사용자 이름을 입력해주세요",
                    style = TextStyle(fontSize = 14.sp),
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(50.dp),
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = field,
                focusedContainerColor = field,
                focusedIndicatorColor = primaryActive,
                unfocusedIndicatorColor = primaryDefault
            ),
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
        )

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                if (signUpAvailability) signUp()
            },
            colors = if (signUpAvailability) ButtonDefaults.buttonColors(primaryDefault85) else ButtonDefaults.buttonColors(
                primaryActive
            ),
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            Text(
                "완료",
                color = Color.White,
                style = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium)
            )
        }

        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
private fun CircleWithStroke(
) {
    Canvas(modifier = Modifier.size(133.dp)) {
        val canvasWidth = size.width
        val center = center
        val radius = canvasWidth / 2f

        drawCircle(
            color = primaryDefault,
            radius = radius,
            center = center,
            style = Stroke(12f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    SignUpScreen(signUp = { })
}