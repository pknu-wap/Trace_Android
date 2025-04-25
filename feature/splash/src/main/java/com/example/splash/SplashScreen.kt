package com.example.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun SplashRoute(
    navigateToHome: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SplashViewModel.SplashEvent.NavigateToHome -> navigateToHome()
            }
        }
    }

    SplashScreen()
}

@Composable
private fun SplashScreen() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))

        Text("스플래시")

        Spacer(Modifier.weight(1f))
    }
}