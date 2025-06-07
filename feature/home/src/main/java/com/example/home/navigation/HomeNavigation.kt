package com.example.home.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.common.ui.defaultSlideFadeIn
import com.example.common.ui.defaultSlideFadeOut
import com.example.common.ui.etaSlideIn
import com.example.common.ui.etaSlideOut
import com.example.home.graph.home.HomeRoute
import com.example.home.graph.post.PostRoute
import com.example.home.graph.search.SearchRoute
import com.example.home.graph.updatepost.UpdatePostRoute
import com.example.home.graph.writepost.WritePostRoute
import com.example.navigation.HomeBaseRoute
import com.example.navigation.HomeGraph

fun NavController.navigateToHome(navOptions: NavOptions? = null) {
    navigate(HomeGraph.HomeRoute, navOptions)
}

fun NavController.navigateToSearch(navOptions: NavOptions? = null) {
    navigate(HomeGraph.SearchRoute, navOptions)
}

fun NavController.navigateToWritePost(navOptions: NavOptions? = null) {
    navigate(HomeGraph.WritePostRoute, navOptions)
}

fun NavController.navigateToPost(postId: Int, navOptions: NavOptions? = null) {
    navigate(HomeGraph.PostRoute(postId), navOptions)
}

fun NavController.navigateToUpdatePost(postId: Int, navOptions: NavOptions? = null) {
    navigate(HomeGraph.UpdatePostRoute(postId), navOptions)
}

fun NavGraphBuilder.homeNavGraph(
    navigateToSearch: () -> Unit,
    navigateToPost: (Int) -> Unit,
    navigateToWritePost: () -> Unit,
    navigateToUpdatePost: (Int) -> Unit,
    navigateToPostReplacing: (Int) -> Unit,
    navigateBack: () -> Unit
) {
    navigation<HomeBaseRoute>(startDestination = HomeGraph.HomeRoute) {
        composable<HomeGraph.HomeRoute>(
            enterTransition = {
                defaultSlideFadeIn()
            },
            exitTransition = {
                defaultSlideFadeOut()
            },
            popEnterTransition = {
                defaultSlideFadeIn()
            },
            popExitTransition = {
                defaultSlideFadeOut()
            }

        ) {
            HomeRoute(
                navigateToPost = navigateToPost,
                navigateToWritePost = navigateToWritePost,
                navigateToSearch = navigateToSearch
            )
        }

        composable<HomeGraph.SearchRoute>(
            enterTransition = {
                defaultSlideFadeIn()
            },
            exitTransition = {
                defaultSlideFadeOut()
            },
            popEnterTransition = {
                defaultSlideFadeIn()
            },
            popExitTransition = {
                defaultSlideFadeOut()
            }
        ) {
            SearchRoute(
                navigateBack = navigateBack,
                navigateToPost = navigateToPost,
            )
        }

        composable<HomeGraph.WritePostRoute>(
            enterTransition = {
                defaultSlideFadeIn()
            },
            exitTransition = {
                defaultSlideFadeOut()
            },
            popEnterTransition = {
                defaultSlideFadeIn()
            },
            popExitTransition = {
                defaultSlideFadeOut()
            }
        ) {
            WritePostRoute(
                navigateToPost = navigateToPostReplacing,
                navigateBack = navigateBack
            )
        }

        composable<HomeGraph.PostRoute>(
            enterTransition = { etaSlideIn(isBack = false) },
            exitTransition = { etaSlideOut(isBack = false) },
            popEnterTransition = { etaSlideIn(isBack = true) },
            popExitTransition = { etaSlideOut(isBack = true) }
        ) {
            PostRoute(
                navigateBack = navigateBack,
                navigateToUpdatePost = navigateToUpdatePost
            )
        }

        composable<HomeGraph.UpdatePostRoute>(
            enterTransition = {
                defaultSlideFadeIn()
            },
            exitTransition = {
                defaultSlideFadeOut()
            },
            popEnterTransition = {
                defaultSlideFadeIn()
            },
            popExitTransition = {
                defaultSlideFadeOut()
            }
        ) {
            UpdatePostRoute(
                navigateBack = navigateBack,
                navigateToPost = navigateToPostReplacing
            )
        }

    }
}

