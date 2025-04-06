package com.example.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.auth.graph.editProfile.EditProfileRoute
import com.example.auth.graph.login.LoginRoute
import com.example.navigation.LoginRoute
import com.example.navigation.EditProfileRoute


fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(LoginRoute, navOptions)
}

fun NavController.navigateToSignUp(navOptions: NavOptions? = null) {
    navigate(EditProfileRoute, navOptions)
}


fun NavGraphBuilder.loginScreen(
    navigateToHome: () -> Unit,
    navigateToEditProfile: () -> Unit
) {
    composable<LoginRoute> {
        LoginRoute(
            navigateToHome = navigateToHome,
            navigateToEditProfile = navigateToEditProfile
        )
    }
}

fun NavGraphBuilder.editProfileScreen(
    navigateToHome: () -> Unit,
    navigateBack: () -> Unit
) {
    composable<EditProfileRoute> {
        EditProfileRoute(
            navigateToHome = navigateToHome,
            navigateBack = navigateBack
        )
    }
}