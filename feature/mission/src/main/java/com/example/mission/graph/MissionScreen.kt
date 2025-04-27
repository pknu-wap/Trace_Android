package com.example.mission.graph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun MissionRoute(
    viewModel : MissionViewModel = hiltViewModel()
) {
    MissionScreen()
}

@Composable
private fun MissionScreen() {

}