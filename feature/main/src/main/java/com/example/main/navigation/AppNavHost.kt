package com.example.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.auth.navigation.authNavGraph
import com.example.auth.navigation.navigateToEditProfile
import com.example.auth.navigation.navigateToLogin
import com.example.home.navigation.homeNavGraph
import com.example.home.navigation.navigateToHome
import com.example.home.navigation.navigateToPost
import com.example.home.navigation.navigateToWritePost
import com.example.mission.navigation.missionNavGraph
import com.example.mypage.navigation.myPageNavGraph
import com.example.mypage.navigation.navigateToSetting
import com.example.mypage.navigation.navigateToUpdateProfile
import com.example.navigation.HomeGraph
import com.example.navigation.SplashRoute
import com.example.splash.navigation.splashScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = SplashRoute,
        modifier = modifier,
    ) {
        val currentRoute = navController.currentDestination?.route

        splashScreen(
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo(0) { inclusive = true }
                    }
                )
            },
            navigateToLogin = {
                navController.navigateToLogin(
                    navOptions {
                        popUpTo(0) { inclusive = true }
                    }
                )
            }
        )

        authNavGraph(
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo(0) { inclusive = true }
                    }
                )
            },
            navigateBack = { navigateBack(navController) },
            navigateToEditProfile = { signUpToken, providerId ->
                navController.navigateToEditProfile(signUpToken, providerId)
            }
        )

        homeNavGraph(
            navigateToPost = { postId ->
                navController.navigateToPost(postId)
            },
            navigateToWrittenPost = { postId ->
                navController.navigateToPost(postId, navOptions {
                    popUpTo<HomeGraph.WritePostRoute> {
                        inclusive = true
                    }
                })
            },
            navigateToWritePost = {
                navController.navigateToWritePost()
            },
            navigateBack = { navigateBack(navController) }
        )

        missionNavGraph()

        myPageNavGraph(
            navigateToPost = { postId -> navController.navigateToPost(postId) },
            navigateToUpdateProfile = { navController.navigateToUpdateProfile() },
            navigateToSetting = { navController.navigateToSetting() },
            navigateBack = { navigateBack(navController) },
            navigateToLogin = {
                navController.navigateToLogin(navOptions {
                    popUpTo(0) { inclusive = true }
                })
            }
        )

    }
}

private fun navigateBack(
    navController: NavHostController
) {
    navController.popBackStack()
}
 