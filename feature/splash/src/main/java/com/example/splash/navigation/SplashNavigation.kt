package com.example.splash.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.navigation.SplashRoute
import com.example.splash.SplashRoute


fun NavGraphBuilder.splashScreen(

) {
    composable<SplashRoute> {
        SplashRoute()
    }
}