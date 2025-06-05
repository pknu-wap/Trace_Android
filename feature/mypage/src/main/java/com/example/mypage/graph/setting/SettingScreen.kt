package com.example.mypage.graph.setting

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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common.event.TraceEvent
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.component.CheckCancelDialog
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.Red
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White
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
                is SettingEvent.Logout -> navigateToLogin()
                is SettingEvent.UnregisterUserSuccess -> navigateToLogin()
                is SettingEvent.UnregisterUserFailure -> {
                    viewModel.eventHelper.sendEvent(TraceEvent.ShowSnackBar("회원 탈퇴에 실패했습니다."))
                }
            }
        }
    }

    SettingScreen(
        navigateBack = navigateBack,
        logout = viewModel::logout,
        unregisterUser = viewModel::unregisterUser
    )
}

@Composable
private fun SettingScreen(
    navigateBack: () -> Unit,
    logout: () -> Unit,
    unregisterUser: () -> Unit
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    var showUnRegisterUserDialog by remember { mutableStateOf(false) }

    if (showLogoutDialog) {
        CheckCancelDialog(
            onCheck = {
                logout()
                showLogoutDialog = false
            },
            onDismiss = { showLogoutDialog = false },
            dialogText = "정말 로그아웃 하시겠습니까?"
        )
    }

    if (showUnRegisterUserDialog) {
        CheckCancelDialog(
            onCheck = {
                unregisterUser()
                showUnRegisterUserDialog = false
            },
            onDismiss = { showUnRegisterUserDialog = false },
            dialogText = "정말 회원탈퇴 하시겠습니까?"
        )
    }


    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 80.dp, start = 30.dp, end = 20.dp)
        ) {
            Text("로그아웃", style = TraceTheme.typography.bodyMR.copy(fontSize = 20.sp, lineHeight = 24.sp), modifier = Modifier.clickable {
                showLogoutDialog = true
            })

            Spacer(Modifier.height(17.dp))

            Text(
                "회원탈퇴",
                style = TraceTheme.typography.bodyMR.copy(fontSize = 20.sp, lineHeight = 24.sp),
                color = Red,
                modifier = Modifier.clickable {
                    showUnRegisterUserDialog = true
                })
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(PrimaryDefault)
                .padding(horizontal = 20.dp)
                .height(50.dp)
                .align(Alignment.TopCenter),
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

            Text("설정", style = TraceTheme.typography.headingMB, color = White)

        }
    }


}

@Preview
@Composable
fun SettingScreenPreview() {
    SettingScreen(
        navigateBack = {},
        logout = {},
        unregisterUser = {}
    )
}