package com.example.mypage.graph.editprofile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mypage.graph.editprofile.EditProfileViewModel.EditProfileEvent

@Composable
internal fun EditProfileRoute(
    navigateBack: () -> Unit,
    viewModel: EditProfileViewModel = hiltViewModel(),
) {
    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is EditProfileEvent.NavigateBack -> navigateBack()
            }
        }
    }

    EditProfileScreen(navigateBack)
}

@Composable
private fun EditProfileScreen(
    navigateBack : () -> Unit
) {

}
