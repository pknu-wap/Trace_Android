package com.example.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.auth.graph.editProfile.EditProfileRoute
import com.example.auth.login.LoginRoute
import com.example.navigation.AuthGraph
import com.example.navigation.AuthGraphBaseRoute



fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(AuthGraph.LoginRoute, navOptions)
}

fun NavController.navigateToEditProfile(signUpToken : String, providerId : String, navOptions: NavOptions? = null) {
    navigate(AuthGraph.EditProfileRoute(signUpToken, providerId), navOptions)
}

fun NavGraphBuilder.authNavGraph(
    navigateToHome: () -> Unit,
    navigateToEditProfile: (String, String) -> Unit,
    navigateBack: () -> Unit
) {
    navigation<AuthGraphBaseRoute>(startDestination = AuthGraph.LoginRoute) {
        composable<AuthGraph.LoginRoute> {
            LoginRoute(
                navigateToHome = navigateToHome,
                navigateToEditProfile = { signUpToken, providerId -> navigateToEditProfile(signUpToken, providerId) }
            )
        }

        composable<AuthGraph.EditProfileRoute> {
            EditProfileRoute(
                navigateToHome = navigateToHome,
                navigateBack = navigateBack
            )
        }

    }
}
