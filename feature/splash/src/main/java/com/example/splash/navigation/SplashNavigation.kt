package com.example.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.navigation.SplashRoute
import com.example.splash.SplashRoute

fun NavController.navigateToSplash(navOptions: NavOptions? = null) {
    navigate(SplashRoute, navOptions)
}

fun NavGraphBuilder.splashScreen(
    navigateToHome: () -> Unit,
) {
    composable<SplashRoute> {
        SplashRoute(
            navigateToHome = navigateToHome,
        )
    }
}