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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.component.PostFeed
import com.example.designsystem.theme.GrayLine
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White
import com.example.domain.model.post.PostFeed
import com.example.domain.model.post.TabType
import com.example.home.graph.home.HomeViewModel.HomeEvent
import com.example.home.graph.home.component.HomeDropDownMenu
import com.example.home.graph.home.component.TabSelector
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch


@Composable
internal fun HomeRoute(
    navigateToSearch: () -> Unit,
    navigateToPost: (Int) -> Unit,
    navigateToWritePost: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val postFeeds = viewModel.postPagingFlow.collectAsLazyPagingItems()
    val tabType by viewModel.tabType.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is HomeEvent.NavigateToPost -> navigateToPost(event.postId)
                is HomeEvent.NavigateToWritePost -> navigateToWritePost()
                is HomeEvent.NavigateToSearch -> navigateToSearch()
            }
        }
    }

    HomeScreen(
        postFeeds = postFeeds,
        tabType = tabType,
        onTabTypeChange = viewModel::setTabType,
        navigateToPost = { postId -> viewModel.onEvent(HomeEvent.NavigateToPost(postId)) },
        navigateToWritePost = { viewModel.onEvent(HomeEvent.NavigateToWritePost) },
        navigateToSearch = { viewModel.onEvent(HomeEvent.NavigateToSearch) }
    )
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreen(
    postFeeds: LazyPagingItems<PostFeed>,
    tabType: TabType,
    onTabTypeChange: (TabType) -> Unit,
    navigateToSearch: () -> Unit,
    navigateToPost: (Int) -> Unit,
    navigateToWritePost: () -> Unit,
) {
    var isHomeDropDownMenuExpanded by remember { mutableStateOf(false) }

    val isRefreshing = postFeeds.loadState.refresh is LoadState.Loading

    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { postFeeds.refresh() }
    )

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState)
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 105.dp, start = 20.dp, end = 20.dp)
        ) {
            items(postFeeds.itemCount) { index ->
                postFeeds[index]?.let {
                    PostFeed(
                        postFeed = it,
                        navigateToPost = navigateToPost
                    )

                    Spacer(Modifier.height(8.dp))

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(),
                        thickness = 1.dp,
                        color = GrayLine
                    )
                }

                Spacer(Modifier.height(15.dp))
            }

            item {
                when (val state = postFeeds.loadState.append) {
                    is LoadState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                        ) {
                            CircularProgressIndicator(
                                color = PrimaryDefault, modifier = Modifier.align(
                                    Alignment.Center
                                )
                            )
                        }
                    }

                    is LoadState.Error -> {}

                    else -> {}
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = pullRefreshState,
            contentColor = PrimaryDefault,
            modifier = Modifier.align(Alignment.TopCenter)
        )

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
                    .height(45.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("흔적들", style = TraceTheme.typography.headingMB, color = White)

                Spacer(Modifier.weight(1f))

                Image(
                    painter = painterResource(R.drawable.search_ic),
                    contentDescription = "검색",
                    modifier = Modifier.clickable {
                        navigateToSearch()
                    })

                Spacer(Modifier.width(35.dp))

                Box() {
                    Image(
                        painter = painterResource(R.drawable.menu_ic),
                        contentDescription = "메뉴",
                        modifier = Modifier.clickable {
                            isHomeDropDownMenuExpanded = true
                        })

                    HomeDropDownMenu(
                        expanded = isHomeDropDownMenuExpanded,
                        onDismiss = { isHomeDropDownMenuExpanded = false },
                        onRefresh = {
                            postFeeds.refresh()
                            coroutineScope.launch {
                                listState.scrollToItem(0)
                            }
                        },
                        onWritePost = { navigateToWritePost() }
                    )
                }

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
                    TabType.entries.forEachIndexed { index, type ->
                        TabSelector(
                            type = type,
                            selectedType = tabType,
                            onTabSelected = onTabTypeChange
                        )

                        if (index != TabType.entries.size - 1) Spacer(Modifier.width(12.dp))
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
        tabType = TabType.ALL,
        onTabTypeChange = {},
        navigateToPost = {},
        navigateToWritePost = {},
        navigateToSearch = {},
        postFeeds = fakeLazyPagingItems()
    )
}

@Composable
fun fakeLazyPagingItems(): LazyPagingItems<PostFeed> {
    return flowOf(
        PagingData.from(
            fakePostFeeds
        )
    ).collectAsLazyPagingItems()
}


