package com.example.home.graph.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.designsystem.R
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.GrayLine
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.WarmGray
import com.example.domain.model.home.PostFeed

@Composable
internal fun PostFeed(
    postFeed: PostFeed
) {
    Column(
        modifier = Modifier.padding(bottom = 15.dp)
    ) {
        Text(
            postFeed.title,
            style = TraceTheme.typography.bodyMSB.copy(fontSize = 16.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(Modifier.height(3.dp))

        Text(
            postFeed.content,
            style = TraceTheme.typography.bodySSB.copy(fontSize = 13.sp),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = DarkGray
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                postFeed.nickname,
                style = TraceTheme.typography.bodySSB.copy(fontSize = 11.sp),
                color = WarmGray
            )

            Spacer(Modifier.width(17.dp))

            Text(
                postFeed.createdAt.toString(),
                style = TraceTheme.typography.bodySSB.copy(fontSize = 11.sp),
                color = WarmGray
            )

            Spacer(Modifier.width(12.dp))

            Text(
                "${postFeed.viewCount} 읽음",
                style = TraceTheme.typography.bodySSB.copy(fontSize = 11.sp),
                color = WarmGray
            )

            Spacer(Modifier.width(7.dp))

            Image(painter = painterResource(R.drawable.comment_ic), contentDescription = "댓글 아이콘")

            Spacer(Modifier.width(3.dp))

            Text(
                "${postFeed.commentCount}",
                style = TraceTheme.typography.bodySSB.copy(fontSize = 11.sp),
                color = WarmGray
            )
        }

        Spacer(Modifier.height(8.dp))

        Spacer(Modifier.fillMaxWidth().height(1.dp).background(GrayLine))

    }
}