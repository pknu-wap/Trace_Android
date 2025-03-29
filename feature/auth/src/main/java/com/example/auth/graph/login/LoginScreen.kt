package com.example.auth.graph.login

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.R
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.example.auth.graph.login.LoginViewModel.LoginEvent
import com.example.designsystem.component.checkDialog


@Composable
internal fun LoginRoute(
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val context = LocalContext.current

    var dialogMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is LoginEvent.loginKakao -> loginKakao(
                    context,
                    onSuccess = {
                        idToken -> viewModel.loginKakao(idToken)
                    },
                    onFailure = {
                        dialogMessage = "로그인에 실패했습니다."
                    }
                )
                is LoginEvent.NavigateToHome -> {}
                is LoginEvent.NavigateToSignUp -> {}
            }
        }
    }


    LoginScreen(
       loginKakao = { viewModel.onEvent(LoginEvent.loginKakao) }
    )

    dialogMessage?.let {
        checkDialog(
            onDismiss = { dialogMessage = null },
            dialogText = it
        )
    }
}

@Composable
private fun LoginScreen(
    loginKakao : () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.kakao_login),
            contentDescription = "카카오 로그인",
            modifier = Modifier.clickable {
                loginKakao()
            }
        )

        Spacer(Modifier.height(20.dp))

        Text("둘러보기", style = TextStyle(
            fontSize = 15.sp, fontWeight = FontWeight.SemiBold
        ),
            modifier = Modifier.clickable {

            })



    }
}

private fun loginKakao(
    context : Context,
    onSuccess : (String) -> Unit,
    onFailure : () -> Unit
) {
    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            onFailure()
        } else if (token != null) {
            onSuccess(token.idToken!!)
        }
    }

    UserApiClient.instance.apply {
        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (isKakaoTalkLoginAvailable(context)) {
            loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                   loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    onSuccess(token.idToken!!)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }

}