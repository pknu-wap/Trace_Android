package com.example.auth.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.auth.login.LoginViewModel.LoginEvent
import com.example.common.event.TraceEvent
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.TraceTheme
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient


@Composable
internal fun LoginRoute(
    navigateToHome: () -> Unit,
    navigateToEditProfile: (String, String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> navigateToHome()
                is LoginEvent.NavigateEditProfile -> navigateToEditProfile(
                    event.signUpToken,
                    event.providerId
                )
            }
        }
    }

    LoginScreen(
        viewModel::loginKakao,
        onLoginFailure = { viewModel.eventHelper.sendEvent(TraceEvent.ShowSnackBar("로그인에 실패했습니다")) },
        navigateToHome = { viewModel.onEvent(LoginEvent.NavigateToHome) },
    )
}

@Composable
private fun LoginScreen(
    loginKakao: (String) -> Unit,
    onLoginFailure: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(Modifier.height(160.dp))

        Box(
            modifier = Modifier
                .width(260.dp)
                .height(170.dp)
        ) {
            Text(
                "흔적",
                style = TraceTheme.typography.headingXLB,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            )

            Image(
                painter = painterResource(R.drawable.app_icon_pencil),
                contentDescription = "앱 아이콘",
                modifier = Modifier
                    .size(130.dp)
                    .align(Alignment.TopEnd)
            )
        }



        Spacer(Modifier.height(50.dp))

        Image(
            painter = painterResource(id = R.drawable.kakao_login),
            contentDescription = "카카오 로그인",
            modifier = Modifier.clickable {
                loginKakao(context, loginKakao, onLoginFailure)
            }
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "둘러보기", style = TraceTheme.typography.bodyMM.copy(fontSize = 20.sp),
            modifier = Modifier.clickable {
                navigateToHome()
            })

        Spacer(Modifier.weight(1f))

    }
}

private fun loginKakao(
    context: Context,
    onSuccess: (String) -> Unit,
    onFailure: () -> Unit
) {
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            onFailure()
        } else if (token != null) {
            onSuccess(token.idToken!!)
        }
    }

    UserApiClient.instance.apply {
        if (isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    loginWithKakaoAccount(context, callback = callback)
                } else if (token?.idToken != null) {

                    onSuccess(token.idToken!!)
                    Log.d("idToken", token.idToken!!)

                }
            }
        } else {
            loginWithKakaoAccount(context, callback = callback)
        }
    }
}


@Preview
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        loginKakao = {},
        onLoginFailure = {},
        navigateToHome = {},
    )
}