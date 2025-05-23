package com.example.mypage.graph.mypage

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.component.PostFeed
import com.example.designsystem.component.ProfileImage
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.GrayLine
import com.example.designsystem.theme.TabIndicator
import com.example.designsystem.theme.TraceTheme
import com.example.domain.model.mypage.MyPageTab
import com.example.domain.model.post.PostFeed
import com.example.domain.user.UserInfo
import com.example.mypage.graph.mypage.MyPageViewModel.MyPageEvent


@Composable
internal fun MyPageRoute(
    navigateToPost: (Int) -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToSetting: () -> Unit,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val userInfo by viewModel.userInfo.collectAsStateWithLifecycle()
    val tabType by viewModel.tabType.collectAsStateWithLifecycle()
    val writtenPosts by viewModel.writtenPosts.collectAsStateWithLifecycle()
    val commentedPosts by viewModel.commentedPosts.collectAsStateWithLifecycle()
    val reactedPosts by viewModel.reactedPosts.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventChannel.collect { event ->
            when (event) {
                is MyPageEvent.NavigateToPost -> navigateToPost(event.postId)
                is MyPageEvent.NavigateToEditProfile -> navigateToEditProfile()
                is MyPageEvent.NavigateToSetting -> navigateToSetting()
            }
        }
    }

    MyPageScreen(
        userInfo = userInfo,
        tabType = tabType,
        displayedPosts = when (tabType) {
            MyPageTab.WRITTEN_POSTS -> writtenPosts
            MyPageTab.COMMENTED_POSTS -> commentedPosts
            MyPageTab.REACTED_POSTS -> reactedPosts
        },
        onTabTypeChange = viewModel::setTabType,
        navigateToPost = { postId -> viewModel.onEvent(MyPageEvent.NavigateToPost(postId)) },
        navigateToEditProfile = { viewModel.onEvent(MyPageEvent.NavigateToEditProfile) },
        navigateToSetting = { viewModel.onEvent(MyPageEvent.NavigateToSetting) }
    )

}

@Composable
private fun MyPageScreen(
    userInfo: UserInfo,
    tabType: MyPageTab,
    displayedPosts: List<PostFeed>,
    onTabTypeChange: (MyPageTab) -> Unit,
    navigateToPost: (Int) -> Unit,
    navigateToEditProfile: () -> Unit,
    navigateToSetting: () -> Unit,
) {
    val tabs = MyPageTab.entries

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = 13.dp, start = 20.dp, end = 14.dp
            )
    ) {
        item {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.setting_ic),
                    contentDescription = "설정",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .clickable {
                            navigateToSetting()
                        }
                )
            }

            Spacer(Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileImage(
                    profileImageUrl = userInfo.profileImageUrl,
                    imageSize = if (userInfo.profileImageUrl != null) 96.dp else 86.dp,
                    paddingValue = if (userInfo.profileImageUrl != null) 2.dp else 7.dp,
                    strokeWidth = 10f,
                )

                Spacer(Modifier.width(20.dp))

                Column {
                    Row() {
                        Text(userInfo.name, style = TraceTheme.typography.headingLB)

                        Spacer(Modifier.width(2.dp))

                        Image(
                            painter = painterResource(R.drawable.arrow_right),
                            contentDescription = "설정",
                            modifier = Modifier
                                .clickable {
                                    navigateToEditProfile()
                                }
                        )
                    }

                    Spacer(Modifier.height(10.dp))

                    Text("선행 점수 ${userInfo.goodDeedScore}", style = TraceTheme.typography.bodyML)

                    Spacer(Modifier.height(5.dp))

                    Text(
                        "선행 마크 ${userInfo.goodDeedMarkCount}",
                        style = TraceTheme.typography.bodyML
                    )
                }
            }

            Spacer(Modifier.height(28.dp))

            TabRow(
                selectedTabIndex = tabs.indexOf(tabType),
                containerColor = Background,
                contentColor = Black,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        color = TabIndicator,
                        modifier = Modifier
                            .tabIndicatorOffset(tabPositions[tabs.indexOf(tabType)])
                    )
                }
            ) {
                tabs.forEach { tab ->
                    Tab(
                        selected = tab == tabType,
                        onClick = { onTabTypeChange(tab) },
                        text = {
                            Text(
                                tab.label,
                                style = if (tab == tabType) TraceTheme.typography.bodyMR.copy(
                                    fontSize = 15.sp,
                                    lineHeight = 19.sp
                                ) else TraceTheme.typography.bodyML
                            )
                        }
                    )
                }
            }

            Spacer(Modifier.height(20.dp))
        }

        items(displayedPosts.size) { index ->
            PostFeed(displayedPosts[index], navigateToPost = { navigateToPost(1) })

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
}

@Preview
@Composable
fun MyPageScreenPreview() {
    MyPageScreen(
        userInfo = UserInfo("닉네임", null, 0, 0),
        navigateToEditProfile = {},
        navigateToSetting = {},
        displayedPosts = emptyList(),
        tabType = MyPageTab.WRITTEN_POSTS,
        navigateToPost = {},
        onTabTypeChange = {}
    )
}