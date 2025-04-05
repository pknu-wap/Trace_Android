package com.example.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.example.auth.navigation.loginScreen
import com.example.auth.navigation.navigateToLogin
import com.example.auth.navigation.navigateToSignUp
import com.example.auth.navigation.signUpScreen
import com.example.navigation.LoginRoute
import com.example.navigation.SignUpRoute

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
                navController.navigateToLogin(
                    navOptions {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                )
            },
            navigateToSignUp = {
                navController.navigateToSignUp()
            }
        )
        signUpScreen(
            navigateToHome = {
                navController.navigateToLogin(
                    navOptions {
                        popUpTo<LoginRoute> { inclusive = true }
                    }
                )
            },
            navigateBack = {
                navController.popBackStack()
            }
        )
    }
}