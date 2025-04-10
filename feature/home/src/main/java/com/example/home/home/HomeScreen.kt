package com.example.home.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.home.home.HomeViewModel.HomeEvent
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.White
import com.example.designsystem.R
import com.example.designsystem.theme.Background


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
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text("home")
        }

        FloatingActionButton(
            onClick = navigateToWritePost,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 25.dp, end = 16.dp)
                .shadow(8.dp, shape = CircleShape),
            containerColor = PrimaryDefault,
            contentColor = White,
            shape = CircleShape,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.write_ic),
                contentDescription = "게시글 쓰기",

            )
        }
    }


}

