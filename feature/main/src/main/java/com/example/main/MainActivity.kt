package com.example.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.example.common.event.TraceEvent
import com.example.common.ui.TraceBottomBarAnimation
import com.example.designsystem.component.TraceSnackBar
import com.example.designsystem.component.TraceSnackBarHost
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.TraceTheme
import com.example.home.navigation.navigateToPost
import com.example.main.navigation.AppBottomBar
import com.example.main.navigation.AppNavHost
import com.example.mission.navigation.navigateToMission
import com.example.navigation.AuthGraphBaseRoute
import com.example.navigation.shouldHideBottomBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()
        if (intent.extras != null) { // 백그라운드 알림으로 앱에 진입
            val type = intent.getStringExtra("type")

//            if (type == "mission") {
//                navController.navigateToMission()
//            }
//
//            if (type == "comment" || type == "emotion") {
//                val postId = intent.getStringExtra("postId")?.toIntOrNull()
//                postId?.let {
//                    navController.navigateToPost(postId)
//                }
//            }
        } else {

        }

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            val type = remember(intent) { intent.getStringExtra("type") }
            val postId = remember(intent) { intent.getStringExtra("postId")?.toIntOrNull() }


            LaunchedEffect(type, postId) {
                when (type) {
                    "mission" -> {
                        navController.navigateToMission()
                    }

                    "comment", "emotion" -> {
                        postId?.let {
                            navController.navigateToPost(it)
                        }
                    }
                }
            }

            val currentDestination = navController.currentBackStackEntryAsState()
                .value?.destination
            val snackBarHostState = remember { SnackbarHostState() }



            LaunchedEffect(Unit) {
                lifecycleScope.launch {
                    lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED, {
                        launch {

                        }

                        launch {
                            viewModel.eventHelper.eventChannel.collect { event ->
                                when (event) {
                                    is TraceEvent.ShowSnackBar -> snackBarHostState.showSnackbar(
                                        event.message
                                    )
                                }
                            }
                        }
                    })
                }

            }

            TraceTheme {
                val imeIsShown = WindowInsets.isImeVisible
                val statusBarPadding =
                    WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                val bottomPadding =
                    if (imeIsShown) 0.dp else WindowInsets.navigationBars.asPaddingValues()
                        .calculateBottomPadding()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(
                        top = statusBarPadding,
                        bottom = bottomPadding
                    ),
                    snackbarHost = {
                        TraceSnackBarHost(
                            hostState = snackBarHostState,
                            snackbar = { snackBarData -> TraceSnackBar(snackBarData) }
                        )
                    },
                    containerColor = Background,
                    bottomBar = {
                        TraceBottomBarAnimation(
                            visible = currentDestination?.shouldHideBottomBar() == false,
                            modifier = Modifier.navigationBarsPadding(),
                        ) {
                            AppBottomBar(
                                currentDestination = currentDestination,
                                navigateToBottomNaviDestination = { bottomNaviDestination ->
                                    navController.navigate(
                                        bottomNaviDestination,
                                        navOptions = navOptions {
                                            popUpTo(0) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        })
                                }
                            )
                        }
                    }
                ) { innerPadding ->
                    AppNavHost(navController, Modifier.padding(innerPadding))
                }
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)

        val type = intent.getStringExtra("type")

        type?.let {
            if (type == "mission") {
                Log.d("traceIntent", "mission")
            }

            if (type == "comment" || type == "emotion") {
                val postId = intent.getStringExtra("postId")?.toIntOrNull()
                postId?.let {
                    Log.d("traceIntent", postId.toString())
                }
            }
        }

    }
}

private fun handleNavigationEvent(
    navController: NavController,
    event: NavigationEvent
) {
    when (event) {
        is NavigationEvent.To -> {
            val navOptions = navOptions {
                if (event.popUpTo) {
                    popUpTo(
                        navController.currentBackStackEntry?.destination?.route
                            ?: navController.graph.startDestinationRoute
                            ?: AuthGraphBaseRoute.toString()
                    ) { inclusive = true }
                }
                launchSingleTop = true
            }

            navController.navigate(
                route = event.route,
                navOptions = navOptions
            )
        }

        is NavigationEvent.Up -> navController.navigateUp()

        is NavigationEvent.TopLevelTo -> {
            val topLevelNavOptions = navOptions {
                popUpTo(0) { inclusive = true }
                launchSingleTop = true
            }

            navController.navigate(
                route = event.route,
                navOptions = topLevelNavOptions
            )
        }

        is BottomBarTo -> {
            val topLevelNavOptions = navOptions {
                popUpTo(MatchingGraph.MatchingRoute) { saveState = true }
                launchSingleTop = true
                restoreState = true
            }

            navController.navigate(
                route = event.route,
                navOptions = topLevelNavOptions
            )
        }
    }
}

