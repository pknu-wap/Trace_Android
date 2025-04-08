package com.example.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.rememberNavController
import com.example.common.event.TraceEvent
import com.example.designsystem.component.TraceSnackBar
import com.example.designsystem.component.TraceSnackBarHost
import com.example.designsystem.theme.TraceTheme
import com.example.main.navigation.AppNavHost
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
                    containerColor = Color.White,
                ) { innerPadding ->
                    AppNavHost(navController, Modifier.padding(innerPadding))
                }

            }
        }

    }
}

