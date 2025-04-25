package com.example.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.theme.TraceTheme
import com.example.splash.SplashViewModel.SplashEvent

@Composable
internal fun SplashRoute(
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is SplashEvent.NavigateToHome -> navigateToHome()
                is SplashEvent.NavigateToLogin -> navigateToLogin()
            }
        }
    }

    SplashScreen()
}

@Composable
private fun SplashScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))

        Text("스플래시", style = TraceTheme.typography.headingLB)

        Spacer(Modifier.weight(1f))
    }
}