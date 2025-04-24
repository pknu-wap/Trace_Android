package com.example.mypage.graph.setting

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mypage.graph.setting.SettingViewModel.SettingEvent

@Composable
internal fun SettingRoute(
    navigateBack: () -> Unit,
    viewModel: SettingViewModel = hiltViewModel(),
) {
    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SettingEvent.NavigateBack -> navigateBack()
            }
        }
    }

    SettingScreen(navigateBack)
}

@Composable
private fun SettingScreen(
    navigateBack : () -> Unit
) {

}
