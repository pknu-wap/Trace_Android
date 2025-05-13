package com.example.home.graph.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.Background
import com.example.designsystem.theme.Black
import com.example.designsystem.theme.Gray
import com.example.designsystem.theme.GrayLine
import com.example.designsystem.theme.LightGray
import com.example.designsystem.theme.TabIndicator
import com.example.designsystem.theme.TraceTheme
import com.example.domain.model.post.PostFeed
import com.example.domain.model.post.SearchType
import com.example.home.graph.home.component.PostFeed

@Composable
internal fun SearchResultView(
    searchType: SearchType,
    onSearchTypeChange: (SearchType) -> Unit,
    displayedPosts: List<PostFeed>,
    navigateToPost: (Int) -> Unit,
) {
    val tabs = SearchType.entries

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background)
    ) {
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .clickable {

                },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("전체 글", style = TraceTheme.typography.bodySSB)

            Spacer(Modifier.width(5.dp))

            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "드롭다운 아이콘"
            )
        }

        Spacer(Modifier.height(50.dp))

        TabRow(
            selectedTabIndex = tabs.indexOf(searchType),
            containerColor = Background,
            contentColor = Black,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    color = TabIndicator,
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[tabs.indexOf(searchType)])
                )
            }
        ) {
            tabs.forEach { tab ->
                Tab(
                    selected = tab == searchType,
                    onClick = { onSearchTypeChange(tab) },
                    text = {
                        Text(
                            tab.label,
                            style = TraceTheme.typography.bodyXMR
                        )
                    }
                )
            }
        }

        Spacer(Modifier.height(15.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text("${displayedPosts.size}개", style = TraceTheme.typography.bodySR)

            Spacer(Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(R.drawable.sort_ic),
                    contentDescription = "정렬",
                    tint = LightGray
                )

                Spacer(Modifier.width(5.dp))

                Text("정렬", style = TraceTheme.typography.bodySM, color = LightGray)
            }
        }

        Spacer(Modifier.height(25.dp))

        if (displayedPosts.isEmpty()) {

            Spacer(Modifier.height(100.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("검색 결과가 없습니다.", style = TraceTheme.typography.bodySM, color = Gray, modifier = Modifier.align(
                    Alignment.Center))
            }

            Spacer(Modifier.height(3.dp))

            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    "다른 검색어를 입력하거나 필터를 다시 선택해 주세요.",
                    style = TraceTheme.typography.bodySM,
                    color = Gray,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }

        displayedPosts.forEachIndexed { index, post ->
            PostFeed(post, onClick = { navigateToPost(1) })

            Spacer(Modifier.height(8.dp))

            if(index != displayedPosts.size -1) {
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
}
