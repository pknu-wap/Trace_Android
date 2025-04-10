package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.common.event.TraceEvent
import com.example.common.ui.TraceBottomBarAnimation
import com.example.designsystem.component.TraceSnackBar
import com.example.designsystem.component.TraceSnackBarHost
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.TraceTheme
import com.example.main.navigation.AppBottomBar
import com.example.main.navigation.AppNavHost
import com.example.navigation.shouldHideBottomBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val navController = rememberNavController()
            val currentDestination = navController.currentBackStackEntryAsState()
                .value?.destination
            val snackBarHostState = remember { SnackbarHostState() }

            LaunchedEffect(true) {
                launch {
                    viewModel.eventHelper.eventChannel.collect { event ->
                        when (event) {
                            is TraceEvent.ShowSnackBar -> snackBarHostState.showSnackbar(event.message)
                        }
                    }
                }
            }


            TraceTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    snackbarHost = {
                        TraceSnackBarHost(
                            hostState = snackBarHostState,
                            snackbar = { snackBarData -> TraceSnackBar(snackBarData) }
                        )
                    },
                    containerColor = Background,
                    bottomBar = {
                        TraceBottomBarAnimation(
                            visible = currentDestination?.shouldHideBottomBar() == false
                        ) {
                            AppBottomBar(
                                currentDestination = currentDestination,
                                navigateToBottomNaviDestination = { bottomNaviDestination ->
                                    navController.navigate(bottomNaviDestination)
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
}

