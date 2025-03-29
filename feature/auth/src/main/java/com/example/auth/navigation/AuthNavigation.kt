package com.example.auth.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.auth.graph.login.LoginRoute
import com.example.navigation.AuthGraph
import com.example.navigation.AuthGraphBaseRoute


fun NavGraphBuilder.authNavGraph() {
    navigation<AuthGraphBaseRoute>(startDestination = AuthGraph.LoginRoute) {
        composable<AuthGraph.LoginRoute> {
            LoginRoute()
        }

//        composable<AuthGraph.SignUpRoute> {
//            SignUpRoute()
//        }
    }
}