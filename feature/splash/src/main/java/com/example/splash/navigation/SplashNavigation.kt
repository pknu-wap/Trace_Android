package com.example.splash.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.navigation.SplashRoute
import com.example.splash.SplashRoute


fun NavGraphBuilder.splashScreen(
    navigateToHome: () -> Unit,
) {
    composable<SplashRoute> {
        SplashRoute(
            navigateToHome = navigateToHome,
        )
    }
}