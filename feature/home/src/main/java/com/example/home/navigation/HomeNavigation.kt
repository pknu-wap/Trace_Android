package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.home.graph.home.HomeRoute
import com.example.home.graph.post.PostRoute
import com.example.home.graph.updatepost.UpdatePostRoute
import com.example.home.graph.writepost.WritePostRoute
import com.example.navigation.HomeBaseRoute
import com.example.navigation.HomeGraph

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeGraph.HomeRoute, navOptions)
}

fun NavController.navigateToWritePost(navOptions: NavOptions? = null) {
    navigate(HomeGraph.WritePostRoute, navOptions)
}

fun NavController.navigateToPost(postId : Int, navOptions: NavOptions? = null) {
    navigate(HomeGraph.PostRoute(postId), navOptions)
}

fun NavController.navigateToUpdatePost(postId : Int, navOptions: NavOptions? = null) {
    navigate(HomeGraph.UpdatePostRoute(postId), navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateToPost: (Int) -> Unit,
    navigateToWritePost: () -> Unit,
    navigateToUpdatePost : (Int) -> Unit,
    navigateBack: () -> Unit
) {
    navigation<HomeBaseRoute>(startDestination = HomeGraph.HomeRoute) {
        composable<HomeGraph.HomeRoute> {
            HomeRoute(
                navigateToPost = navigateToPost,
                navigateToUpdatePost = navigateToUpdatePost,
                navigateToWritePost = navigateToWritePost
            )
        }

        composable<HomeGraph.WritePostRoute> {
            WritePostRoute(
                navigateToPost = navigateToPost,
                navigateBack = navigateBack
            )
        }

        composable<HomeGraph.PostRoute> {
            PostRoute(
                navigateBack = navigateBack,
                navigateToUpdatePost = navigateToUpdatePost
            )
        }

        composable<HomeGraph.UpdatePostRoute> {
            UpdatePostRoute(
                navigateBack = navigateBack,
                navigateToPost = navigateToPost
            )
        }
    }
}

