package com.example.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.auth.graph.login.LoginRoute
import com.example.auth.graph.signup.SignUpRoute
import com.example.navigation.LoginRoute
import com.example.navigation.SignUpRoute


fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(LoginRoute, navOptions)
}

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    navigate(SignUpRoute, navOptions)
}


fun NavGraphBuilder.loginScreen(
    navigateToHome: () -> Unit,
    navigateToSignUp: () -> Unit
) {
    composable<LoginRoute> {
        LoginRoute(
            navigateToHome = navigateToHome,
            navigateToSignUp = navigateToSignUp
        )
    }
}

fun NavGraphBuilder.signUpScreen(
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit
) {
    composable<SignUpRoute> {
        SignUpRoute(
            navigateToHome = navigateToHome,
            navigateBack = navigateBack
        )
    }
}