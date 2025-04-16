package com.example.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.auth.navigation.editProfileScreen
import com.example.auth.navigation.loginScreen
import com.example.auth.navigation.navigateToEditProfile
import com.example.home.navigation.homeScreen
import com.example.home.navigation.navigateToHome
import com.example.home.navigation.navigateToPost
import com.example.home.navigation.navigateToWritePost
import com.example.home.navigation.postScreen
import com.example.home.navigation.writePostScreen
import com.example.navigation.EditProfileRoute
import com.example.navigation.LoginRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {

    NavHost(
        navController = navController,
        startDestination = LoginRoute,
        modifier = modifier,
    ) {
        loginScreen(
            navigateToHome = {
                navController.navigateToHome(
                    navOptions {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                )
            },
            navigateToEditProfile = {
                navController.navigateToEditProfile()
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