package com.example.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import kotlin.reflect.KClass

private val HIDDEN_BOTTOM_BAR_ROUTES = setOf(
    AuthGraph.LoginRoute::class,
    AuthGraph.EditProfileRoute::class,
    HomeGraph.WritePostRoute::class,
    HomeGraph.PostRoute::class
)

fun NavDestination?.shouldHideBottomBar(): Boolean =
    this?.route?.let { route ->
        HIDDEN_BOTTOM_BAR_ROUTES.any { hiddenRoute ->
            route.startsWith(hiddenRoute.qualifiedName ?: "")
        }
    } ?: false

fun NavDestination?.isRouteInHierarchy(route: KClass<*>): Boolean =
    this?.hierarchy?.any { it.hasRoute(route) } == true

fun NavDestination?.eqaulsRoute(route: KClass<*>): Boolean =
    this?.route?.startsWith(route.qualifiedName ?: "") == true


