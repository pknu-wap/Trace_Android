package com.example.home.graph.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.GrayLine
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White
import com.example.domain.model.post.PostFeed
import com.example.domain.model.post.PostType
import com.example.home.graph.home.HomeViewModel.HomeEvent
import com.example.home.graph.home.component.PostFeed
import com.example.home.graph.home.component.TabSelector


@Composable
internal fun HomeRoute(
    navigateToPost: () -> Unit,
    navigateToWritePost: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val postFeeds by viewModel.postFeeds.collectAsStateWithLifecycle()
    val tabType by viewModel.tabType.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is HomeEvent.NavigateToPost -> navigateToPost()
                is HomeEvent.NavigateToWritePost -> navigateToWritePost()
            }
        }
    }

    HomeScreen(
        postFeeds = postFeeds,
        tabType = tabType,
        onTabTypeChange = viewModel::setTabType,
        navigateToPost = { viewModel.onEvent(HomeEvent.NavigateToPost) },
        navigateToWritePost = { viewModel.onEvent(HomeEvent.NavigateToWritePost) })
}


@Composable
private fun HomeScreen(
    postFeeds: List<PostFeed>,
    tabType: PostType,
    onTabTypeChange: (PostType) -> Unit,
    navigateToPost: () -> Unit,
    navigateToWritePost: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 105.dp, start = 20.dp, end = 20.dp)
        ) {
            items(postFeeds.size) { index ->
                PostFeed(
                    postFeed = postFeeds[index],
                    onClick = navigateToPost
                )

                Spacer(Modifier.height(8.dp))

                Spacer(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(GrayLine)
                )

                Spacer(Modifier.height(15.dp))
            }
        }

        Column(
            modifier = Modifier.align(Alignment.TopCenter)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        PrimaryDefault
                    )
                    .padding(horizontal = 20.dp)
                    .size(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("흔적들", style = TraceTheme.typography.headingMB, color = White)

                Spacer(Modifier.weight(1f))

                Image(
                    painter = painterResource(R.drawable.search_ic),
                    contentDescription = "검색",
                    modifier = Modifier.clickable {

                    })

                Spacer(Modifier.width(35.dp))

                Image(
                    painter = painterResource(R.drawable.menu_ic),
                    contentDescription = "메뉴",
                    modifier = Modifier.clickable {

                    })
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .size(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.clickable {

                    },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    PostType.entries.forEachIndexed { index, type ->
                        TabSelector(
                            type = type,
                            selectedType = tabType,
                            onTabSelected = onTabTypeChange
                        )

                        if (index != PostType.entries.size - 1) Spacer(Modifier.width(12.dp))
                    }
                }
            }
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


@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        navigateToPost = {},
        navigateToWritePost = {},
        postFeeds = fakePostFeeds,
        tabType = PostType.ALL,
        onTabTypeChange = {},
    )
}

