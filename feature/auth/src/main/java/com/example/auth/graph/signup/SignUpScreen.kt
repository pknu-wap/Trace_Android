package com.example.auth.graph.editProfile


import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.rememberAsyncImagePainter
import com.example.auth.graph.signup.SignUpViewModel

import com.example.auth.graph.signup.SignUpViewModel.SignUpEvent
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.background
import com.example.designsystem.theme.field
import com.example.designsystem.theme.primaryActive
import com.example.designsystem.theme.primaryDefault

@Composable
internal fun EditProfileRoute(
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit,
    viewModel: SignUpViewModel = hiltViewModel(),
) {

    val name by viewModel.nameText.collectAsStateWithLifecycle()
    val isNameValid by viewModel.isNameValid.collectAsStateWithLifecycle()
    val profileImage by viewModel.profileImage.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SignUpEvent.SignUpSuccess -> {
                    navigateToHome()
                }
            }
        }
    }

    EditProfileScreen(
        name,
        profileImage,
        isNameValid,
        viewModel::setName,
        viewModel::setProfileImage,
        viewModel::registerUser,
        navigateBack,
    )

}

@Composable
private fun EditProfileScreen(
    name: String,
    profileImage: Uri?,
    isNameValid: Boolean,
    onNameChange: (String) -> Unit,
    onProfileImageChange: (Uri) -> Unit,
    registerUser: () -> Unit,
    navigateBack: () -> Unit
) {

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            if (uri != null) {
                onProfileImageChange(uri)
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        val signUpAvailability = name.isNotEmpty() && isNameValid
        val keyboardController = LocalSoftwareKeyboardController.current

        Spacer(Modifier.height(16.dp))

        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = "뒤로 가기",
            modifier = Modifier
                .size(50.dp)
                .align(Alignment.Start)
                .padding(start = 9.dp)
                .clickable() {
                    navigateBack()
                }
        )

        Spacer(Modifier.height(25.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(style = SpanStyle(color = primaryDefault)) {
                    append("프로필")
                }
                append(" 설정")
            },
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.bookk_myungjo_bold))
            ),
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp)
        )

        Spacer(Modifier.height(26.dp))

        Box() {
            CircleWithStroke()

            ProfileImage(profileImage)

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 13.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.camera_ic),
                    contentDescription = "갤러리 불러오기",
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable {
                            launcher.launch("image/*")
                        }
                )
            }

        }

        Spacer(Modifier.height(46.dp))

        Text(
            "사용자 이름",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.bookk_myungjo_bold))
            ),
            color = primaryDefault,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 20.dp)
        )

        Spacer(Modifier.height(12.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { onNameChange(it) },
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
            textStyle = TextStyle(fontSize = 14.sp),
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

        if (name.isNotEmpty() && !isNameValid) {
            Spacer(Modifier.height(2.dp))

            Text(
                "닉네임은 최소 2자, 최대 12자까지 가능해요",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Red,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(20.dp)
                    .offset(y = -10.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        Button(
            onClick = {
                if (signUpAvailability) registerUser()
            },
            colors = if (!signUpAvailability) ButtonDefaults.buttonColors(primaryDefault.copy(alpha = 0.65F)) else ButtonDefaults.buttonColors(
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
private fun CircleWithStroke() {
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

@Composable
fun ProfileImage(imageUri: Uri?) {
    val painter = if (imageUri != null) {
        rememberAsyncImagePainter(imageUri)
    } else {
        painterResource(id = R.drawable.default_profile)
    }

    if(imageUri != null) {
        Box(Modifier.padding(2.dp)) {
            Image(
                painter = painter,
                contentDescription = "프로필 이미지",
                modifier = Modifier
                    .size(129.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

    }
    else {
        Box(Modifier.padding(9.dp)) {
            Image(
                painter = painter,
                contentDescription = "프로필 이미지",
                modifier = Modifier
                    .size(115.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }
    }


}

//
//@Preview(showBackground = true)
//@Composable
//fun SignUpScreenPreview() {
//    EditProfileScreen(
//        name = "",
//        isNameValid = true,
//        onNameChange = {},
//        onProfileImageChange = {},
//        registerUser = {},
//        navigateBack = {},)
//}