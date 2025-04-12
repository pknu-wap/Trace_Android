package com.example.main

import android.os.Bundle
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.common.event.TraceEvent
import com.example.common.ui.TraceBottomBarAnimation
import com.example.designsystem.component.TraceSnackBar
import com.example.designsystem.component.TraceSnackBarHost
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White
import com.example.main.navigation.AppBottomBar
import com.example.main.navigation.AppNavHost
import com.example.navigation.shouldHideBottomBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @OptIn(ExperimentalLayoutApi::class)
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


                val imeIsShown = WindowInsets.isImeVisible
                val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding() // 상태바 여백
                val bottomPadding = if (imeIsShown) 0.dp else WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()  // 하단 네비게이션 바 패딩을 키보드 상태에 따라 처리


                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    contentWindowInsets = WindowInsets(top = statusBarPadding, bottom = bottomPadding),
                    snackbarHost = {
                        TraceSnackBarHost(
                            hostState = snackBarHostState,
                            snackbar = { snackBarData -> TraceSnackBar(snackBarData) }
                        )
                    },
                    containerColor = White,
                    bottomBar = {
                        TraceBottomBarAnimation(
                            visible = currentDestination?.shouldHideBottomBar() == false,
                            modifier = Modifier.navigationBarsPadding(),
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

