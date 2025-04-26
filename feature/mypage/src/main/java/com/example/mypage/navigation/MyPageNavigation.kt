package com.example.mypage.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.mypage.graph.editprofile.EditProfileRoute
import com.example.mypage.graph.mypage.MyPageRoute
import com.example.mypage.graph.setting.SettingRoute
import com.example.navigation.MyPageBaseRoute
import com.example.navigation.MyPageGraph

fun NavController.navigateToMyPage(navOptions: NavOptions? = null) {
    navigate(MyPageGraph.MyPageRoute, navOptions)
}

fun NavController.navigateToEditProfile(navOptions: NavOptions? = null) {
    navigate(MyPageGraph.EditProfileRoute, navOptions)
}

fun NavController.navigateToSetting(navOptions: NavOptions? = null) {
    navigate(MyPageGraph.SettingRoute, navOptions)
}

fun NavGraphBuilder.myPageNavGraph(
    navigateToPost: () -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateBack: () -> Unit
) {
    navigation<MyPageBaseRoute>(startDestination = MyPageGraph.MyPageRoute) {
        composable<MyPageGraph.MyPageRoute> {
            MyPageRoute(
                navigateToPost = navigateToPost,
                navigateToEditProfile = navigateToEditProfile,
                navigateToSetting = navigateToSetting
            )
        }

        composable<MyPageGraph.EditProfileRoute> {
            EditProfileRoute(
                navigateBack = navigateBack
            )
        }

        composable<MyPageGraph.SettingRoute> {
            SettingRoute(
                navigateBack = navigateBack
            )
        }
    }
}