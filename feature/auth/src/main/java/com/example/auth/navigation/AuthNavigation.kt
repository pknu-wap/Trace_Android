package com.example.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.auth.graph.editProfile.EditProfileRoute
import com.example.auth.login.LoginRoute
import com.example.navigation.EditProfileRoute
import com.example.navigation.LoginRoute


fun NavController.navigateToLogin(navOptions: NavOptions? = null) {
    navigate(LoginRoute, navOptions)
}

fun NavController.navigateToEditProfile(idToken : String, navOptions: NavOptions? = null) {
    navigate(EditProfileRoute(idToken), navOptions)
}


fun NavGraphBuilder.loginScreen(
    navigateToHome: () -> Unit,
    navigateToEditProfile: (String) -> Unit
) {
    composable<LoginRoute> {
        LoginRoute(
            navigateToHome = navigateToHome,
            navigateToEditProfile = { idToken -> navigateToEditProfile(idToken) }
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