package com.example.mypage.graph.setting

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.util.clickable
import com.example.designsystem.theme.Red
import com.example.designsystem.theme.TraceTheme
import com.example.mypage.graph.setting.SettingViewModel.SettingEvent

@Composable
internal fun SettingRoute(
    navigateBack: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SettingEvent.NavigateBack -> navigateBack()
                is SettingEvent.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    SettingScreen(
        navigateBack = navigateBack,
        signOut = viewModel::signOut,
        unRegisterUser = viewModel::UnRegisterUser
    )
}

@Composable
private fun SettingScreen(
    navigateBack: () -> Unit,
    signOut: () -> Unit,
    unRegisterUser: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 30.dp, end = 20.dp)
        ) {
            Text("로그아웃", style = TraceTheme.typography.bodyMSB, modifier = Modifier.clickable {

            })

            Spacer(Modifier.height(17.dp))

            Text(
                "회원 탈퇴",
                style = TraceTheme.typography.bodyMSB,
                color = Red,
                modifier = Modifier.clickable {

                })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .padding(start = 15.dp, top = 20.dp),
            verticalAlignment = Alignment.Top
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "뒤로 가기",
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navigateBack()
                    }
            )

            Spacer(Modifier.width(15.dp))

            Text("설정", style = TraceTheme.typography.headingMR)

        }
    }


}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen(
        navigateBack = {},
        signOut = {},
        unRegisterUser = {}
    )
}