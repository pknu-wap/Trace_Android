package com.example.home.graph.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White
import com.example.domain.model.home.PostFeed
import com.example.home.graph.home.HomeViewModel.HomeEvent
import com.example.home.graph.home.HomeViewModel.SortBy
import com.example.home.graph.home.HomeViewModel.TabType
import com.example.home.graph.home.component.PostFeed


@Composable
internal fun HomeRoute(
    navigateToPost: () -> Unit,
    navigateToWritePost: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {

    val postFeeds by viewModel.postFeeds.collectAsStateWithLifecycle()
    val tabType by viewModel.tabType.collectAsStateWithLifecycle()
    val sortBy by viewModel.sortBy.collectAsStateWithLifecycle()

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
        sortBy = sortBy,
        navigateToPost = { viewModel.onEvent(HomeEvent.NavigateToPost) },
        navigateToWritePost = { viewModel.onEvent(HomeEvent.NavigateToWritePost) })
}


@Composable
private fun HomeScreen(
    postFeeds : List<PostFeed>,
    tabType : TabType,
    sortBy: SortBy,
    navigateToPost: () -> Unit,
    navigateToWritePost: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(top = 105.dp, start = 20.dp, end = 20.dp)
        ) {
           items(postFeeds.size) { index ->
               PostFeed(postFeed = postFeeds[index])
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
                    Text("전체 글", style = TraceTheme.typography.bodySSB.copy(fontSize = 15.sp))
                }

                Spacer(Modifier.weight(1f))

                Row(
                    modifier = Modifier.clickable {

                    },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Image(painter = painterResource(R.drawable.sort_ic), contentDescription = "정렬")

                    Spacer(Modifier.width(5.dp))

                    Text("정렬", style = TraceTheme.typography.bodySSB.copy(fontSize = 15.sp))
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
fun homeScreenPreview() {
    HomeScreen(navigateToPost = {}, navigateToWritePost = {}, postFeeds = fakePostFeeds, tabType = TabType.All, sortBy = SortBy.NewestDate)
}

