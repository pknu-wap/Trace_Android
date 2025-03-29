package com.example.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.auth.navigation.authNavGraph
import com.example.navigation.AuthGraphBaseRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AuthGraphBaseRoute,
        modifier = modifier,
    ) {
        authNavGraph()
    }
}