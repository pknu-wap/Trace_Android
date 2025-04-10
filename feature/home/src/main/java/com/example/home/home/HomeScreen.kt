package com.example.home.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.example.home.home.HomeViewModel.HomeEvent
import androidx.hilt.navigation.compose.hiltViewModel


@Composable
internal fun HomeRoute(
    navigateToPost: () -> Unit,
    navigateToWritePost: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is HomeEvent.NavigateToPost -> navigateToPost()
                is HomeEvent.NavigateToWritePost -> navigateToWritePost()
            }
        }
    }

    HomeScreen(
        navigateToPost = { viewModel.onEvent(HomeEvent.NavigateToPost) },
        navigateToWritePost = { viewModel.onEvent(HomeEvent.NavigateToWritePost) }
    )
}


@Composable
private fun HomeScreen(
    navigateToPost: () -> Unit,
    navigateToWritePost: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("home")
    }
}
