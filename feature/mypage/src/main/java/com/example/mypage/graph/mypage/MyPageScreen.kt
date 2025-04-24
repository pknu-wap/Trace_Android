package com.example.mypage.graph.mypage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mypage.graph.mypage.MyPageViewModel.MyPageEvent

@Composable
internal fun MyPageRoute(
    navigateToEditProfile: () -> Unit,
    navigateToSetting: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is MyPageEvent.NavigateToEditProfile -> navigateToEditProfile()
                is MyPageEvent.NavigateToSetting -> navigateToSetting()
            }
        }
    }

}

@Composable
private fun MyPageScreen(
    navigateToEditProfile: () -> Unit,
    navigateToSetting: () -> Unit,
) {

}