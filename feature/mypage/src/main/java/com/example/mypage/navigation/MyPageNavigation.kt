package com.example.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.mypage.graph.mypage.MyPageRoute
import com.example.mypage.graph.setting.SettingRoute
import com.example.mypage.graph.updateprofile.UpdateProfileRoute
import com.example.navigation.MyPageBaseRoute
import com.example.navigation.MyPageGraph

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    navigate(MyPageGraph.MyPageRoute, navOptions)
}

fun NavController.navigateToUpdateProfile(navOptions: NavOptions? = null) {
    navigate(MyPageGraph.UpdateProfileRoute, navOptions)
}

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(MyPageGraph.SettingRoute, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    navigateToLogin : () -> Unit,
    navigateToPost: () -> Unit,
    navigateToUpdateProfile: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateBack: () -> Unit
) {
    navigation<MyPageBaseRoute>(startDestination = MyPageGraph.MyPageRoute) {
        composable<MyPageGraph.MyPageRoute> {
            MyPageRoute(
                navigateToPost = navigateToPost,
                navigateToEditProfile = navigateToUpdateProfile,
                navigateToSetting = navigateToSetting
            )
        }

        composable<MyPageGraph.UpdateProfileRoute> {
            UpdateProfileRoute(
                navigateBack = navigateBack
            )
        }

        composable<MyPageGraph.SettingRoute> {
            SettingRoute(
                navigateToLogin = navigateToLogin,
                navigateBack = navigateBack
            )
        }
    }
}