package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.home.home.HomeRoute
import com.example.navigation.HomeRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeRoute, navOptions)
}

fun NavGraphBuilder.homeScreen(
    navigateToPost: () -> Unit,
    navigateToWritePost: () -> Unit
) {
    composable<HomeRoute> {
        HomeRoute(
            navigateToPost = navigateToPost,
            navigateToWritePost = navigateToWritePost
        )
    }
}