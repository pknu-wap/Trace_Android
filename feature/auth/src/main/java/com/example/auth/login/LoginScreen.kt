package com.example.auth.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
    navigateToEditProfile: (String) -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is LoginEvent.NavigateToHome -> navigateToHome()
                is LoginEvent.NavigateEditProfile -> navigateToEditProfile(event.idToken)
            }
        }
    }

    LoginScreen(
        viewModel::loginKakao,
        onLoginFailure = { viewModel.eventHelper.sendEvent(TraceEvent.ShowSnackBar("로그인에 실패했습니다")) },
        navigateToHome = { viewModel.onEvent(LoginEvent.NavigateToHome) },
        navigateToEditProfile = { viewModel.onEvent(LoginEvent.NavigateEditProfile(idToken = "")) }
    )
}

@Composable
private fun LoginScreen(
    loginKakao: (String) -> Unit,
    onLoginFailure: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_login),
            contentDescription = "카카오 로그인",
            modifier = Modifier.clickable {
                loginKakao(context, loginKakao, onLoginFailure)
//                navigateToEditProfile()
            }
        )

        Spacer(Modifier.height(20.dp))

        Text(
            "둘러보기", style = TraceTheme.typography.bodyMM.copy(fontSize = 20.sp),
            modifier = Modifier.clickable {
                navigateToHome()
            })


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
                } else if (token != null) {

                    onSuccess(token.idToken!!)
                    Log.d("idToken", token.idToken!!)

                }
            }
        } else {
            loginWithKakaoAccount(context, callback = callback)
        }
    }

}