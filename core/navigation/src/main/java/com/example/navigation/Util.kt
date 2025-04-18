package com.example.navigation

import androidx.navigation.NavDestination
import kotlin.reflect.KClass

private val HIDDEN_BOTTOM_BAR_ROUTES = setOf(
    LoginRoute::class,
    EditProfileRoute::class,
    WritePostRoute::class,
    PostRoute::class
)

/**
     * Determines whether the bottom navigation bar should be hidden for the current navigation destination.
     *
     * Returns true if the destination's route matches any of the predefined routes that require the bottom bar to be hidden; otherwise, returns false.
     */
    fun NavDestination?.shouldHideBottomBar(): Boolean =
    this?.route?.let { route ->
        HIDDEN_BOTTOM_BAR_ROUTES.any { hiddenRoute ->
            route.startsWith(hiddenRoute.qualifiedName ?: "")
        }
    } ?: false

fun NavDestination?.eqaulsRoute(route: KClass<*>): Boolean =
    this?.route?.startsWith(route.qualifiedName ?: "") == true

//private val FULL_SCREEN_ROUTES = setOf(
//
//)
