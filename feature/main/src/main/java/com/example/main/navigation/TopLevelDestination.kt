package com.example.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlin.reflect.KClass

enum class TopLevelDestination(
    val route : KClass<*>,
    @DrawableRes val selectedIcon : Int,
    @DrawableRes val unselectedIcon : Int,
    val title : String
) {

}