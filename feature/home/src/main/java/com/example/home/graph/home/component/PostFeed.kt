package com.example.home.graph.home.component

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.WarmGray
import com.example.domain.model.home.PostFeed
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
internal fun PostFeed(
    postFeed: PostFeed,
    onClick: () -> Unit
) {
    if (postFeed.imageUri.isNotEmpty()) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(postFeed.imageUri)
                .crossfade(true)
                .build()
        )

        Box(modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }) {
            Column(
                modifier = Modifier
                    .padding(end = 95.dp)
                    .align(Alignment.CenterStart)
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
                        postFeed.getFormattedTime(),
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

                    Image(
                        painter = painterResource(R.drawable.comment_ic),
                        contentDescription = "댓글 아이콘",
                        colorFilter = ColorFilter.tint(
                            PrimaryDefault
                        )
                    )

                    Spacer(Modifier.width(3.dp))

                    Text(
                        "${postFeed.commentCount}",
                        style = TraceTheme.typography.bodySSB.copy(fontSize = 11.sp),
                        color = WarmGray
                    )
                }

            }


            Box(
                modifier = Modifier
                    .size(75.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .align(Alignment.CenterEnd),
            ) {
                Image(
                    painter = painter,
                    contentDescription = "대표 이미지",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

        }
    } else {
        Column(
            modifier = Modifier.clickable {
                onClick()
            }
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
                    postFeed.getFormattedTime(),
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

                Image(
                    painter = painterResource(R.drawable.comment_ic),
                    contentDescription = "댓글 아이콘",
                    colorFilter = ColorFilter.tint(
                        PrimaryDefault
                    )
                )

                Spacer(Modifier.width(3.dp))

                Text(
                    "${postFeed.commentCount}",
                    style = TraceTheme.typography.bodySSB.copy(fontSize = 11.sp),
                    color = WarmGray
                )
            }

        }
    }
}
