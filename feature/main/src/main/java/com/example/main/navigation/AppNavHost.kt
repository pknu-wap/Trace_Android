package com.example.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.auth.navigation.editProfileScreen
import com.example.auth.navigation.loginScreen
import com.example.auth.navigation.navigateToEditProfile
import com.example.auth.navigation.navigateToLogin
import com.example.home.navigation.homeScreen
import com.example.home.navigation.navigateToHome
import com.example.home.navigation.navigateToPost
import com.example.home.navigation.navigateToWritePost
import com.example.home.navigation.postScreen
import com.example.home.navigation.writePostScreen
import com.example.navigation.EditProfileRoute
import com.example.navigation.LoginRoute
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

        loginScreen(
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                )
            },
            navigateToEditProfile = { idToken ->
                navController.navigateToEditProfile(idToken)
            }
        )
        editProfileScreen(
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo<EditProfileRoute> { inclusive = true }
                    }
                )
            },
            navigateBack = { navController.popBackStack() }
        )
        homeScreen(
            navigateToPost = {
                navController.navigateToPost()
            },
            navigateToWritePost = {
                navController.navigateToWritePost()
            }
        )
        writePostScreen(
            navigateBack = { navController.popBackStack() }
        )
        postScreen(
            navigateBack = { navController.popBackStack() }
        )
    }
}