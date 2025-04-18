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

/**
 * Navigates to the Home screen using the specified navigation options.
 *
 * @param navOptions Optional navigation options to customize the navigation behavior.
 */
fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeRoute, navOptions)
}

/**
 * Navigates to the Write Post screen using the specified navigation options.
 *
 * @param navOptions Optional navigation options to customize the navigation behavior.
 */
fun NavController.navigateToWritePost(navOptions: NavOptions? = null) {
    navigate(WritePostRoute, navOptions)
}

/**
 * Navigates to the Post screen using the specified navigation options.
 *
 * @param navOptions Optional navigation options to customize the navigation behavior.
 */
fun NavController.navigateToPost(navOptions: NavOptions? = null) {
    navigate(PostRoute, navOptions)
}

/**
 * Registers the Home screen destination in the navigation graph.
 *
 * Sets up the HomeRoute composable and provides navigation callbacks for transitioning to the Post and Write Post screens.
 *
 * @param navigateToPost Callback invoked to navigate to the Post screen.
 * @param navigateToWritePost Callback invoked to navigate to the Write Post screen.
 */
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

/**
 * Registers the Write Post screen in the navigation graph.
 *
 * @param navigateBack Callback invoked to navigate back from the Write Post screen.
 */
fun NavGraphBuilder.writePostScreen(
    navigateBack: () -> Unit,
) {
    composable<WritePostRoute> {
        WritePostRoute(
            navigateBack = navigateBack
        )
    }
}

/**
 * Registers the Post screen destination in the navigation graph.
 *
 * @param navigateBack Callback invoked to navigate back from the Post screen.
 */
fun NavGraphBuilder.postScreen(
    navigateBack: () -> Unit,
) {
    composable<PostRoute> {
        PostRoute(
            navigateBack = navigateBack
        )
    }
}