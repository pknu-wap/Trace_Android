package com.example.main.navigation

import androidx.annotation.DrawableRes
import com.example.navigation.HomeRoute
import com.example.navigation.MissionRoute
import com.example.navigation.MyPageRoute
import com.example.designsystem.R
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val route : KClass<*>,
    @DrawableRes val selectedIcon : Int,
    @DrawableRes val unSelectedIcon : Int,
    val contentDescription: String,
    val title : String
) {
    HOME(
        route = HomeRoute::class,
        selectedIcon = R.drawable.home_actvie,
        unSelectedIcon = R.drawable.home,
        contentDescription = "홈",
        title = "홈"
    ),
    Mission(
        route = MissionRoute::class,
        selectedIcon = R.drawable.mission_active,
        unSelectedIcon = R.drawable.mission,
        contentDescription = "미션",
        title = "미션"
    ),
    MyPage(
        route = MyPageRoute::class,
        selectedIcon = R.drawable.my_page_active,
        unSelectedIcon = R.drawable.my_page,
        contentDescription = "마이페이지",
        title = "마이페이지"
    ),
}