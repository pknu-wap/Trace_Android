package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.home.graph.home.HomeRoute
import com.example.home.graph.post.PostRoute
import com.example.home.graph.writepost.WritePostRoute
import com.example.navigation.HomeRoute
import com.example.navigation.PostRoute
import com.example.navigation.WritePostRoute

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeRoute, navOptions)
}

fun NavController.navigateToWritePost(navOptions: NavOptions? = null) {
    navigate(WritePostRoute, navOptions)
}

fun NavController.navigateToPost(navOptions: NavOptions? = null) {
    navigate(PostRoute, navOptions)
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

fun NavGraphBuilder.writePostScreen(
    navigateBack: () -> Unit,
) {
    composable<WritePostRoute> {
        WritePostRoute(
            navigateBack = navigateBack
        )
    }
}

fun NavGraphBuilder.postScreen(
    navigateBack: () -> Unit,
) {
    composable<WritePostRoute> {
        PostRoute(
            navigateBack = navigateBack
        )
    }
}