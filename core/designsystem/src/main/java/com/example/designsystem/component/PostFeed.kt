package com.example.designsystem.component

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
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.domain.model.post.PostFeed
import com.example.domain.model.post.PostType
import java.time.LocalDateTime

@Composable
 fun PostFeed(
    postFeed: PostFeed,
    navigateToPost: (Int) -> Unit
) {
    if (postFeed.imageUrl != null) {
        val painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(postFeed.imageUrl)
                .crossfade(true)
                .build()
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    navigateToPost(postFeed.postId)
                }) {
            Column(
                modifier = Modifier
                    .padding(end = 95.dp)
                    .align(Alignment.CenterStart)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        postFeed.title,
                        style = TraceTheme.typography.bodyMSB.copy(fontSize = 16.sp),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    if (postFeed.isVerified) {
                        Spacer(Modifier.width(4.dp))

                        Image(
                            painter = painterResource(R.drawable.verification_mark),
                            contentDescription = "선행 인증 마크"
                        )
                    }
                }

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
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ProfileImage(
                        profileImageUrl = postFeed.profileImageUrl,
                        imageSize = if (postFeed.profileImageUrl != null) 18.dp else 16.dp,
                        paddingValue = if (postFeed.profileImageUrl != null) 1.dp else 2.dp
                    )


                    Spacer(Modifier.width(6.dp))

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
                        color = PrimaryDefault
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
                navigateToPost(postFeed.postId)
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
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                ProfileImage(
                    profileImageUrl = postFeed.profileImageUrl,
                    imageSize = if (postFeed.profileImageUrl != null) 18.dp else 16.dp,
                    paddingValue = if (postFeed.profileImageUrl != null) 1.dp else 2.dp
                )

                Spacer(Modifier.width(6.dp))

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
                    color = PrimaryDefault
                )
            }

        }
    }
}

@Preview
@Composable
private fun PostFeedPreview() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 15.dp)
    ) {
        PostFeed(
            postFeed = PostFeed(
                postType = PostType.GOOD_DEED,
                title = "깨끗한 공원 만들기",
                content = "오늘 공원에서 쓰레기를 줍고 깨끗한 환경을 만들었습니다. 주변 사람들이 함께 참여해주셨습니다.",
                nickname = "선행자1",
                createdAt = LocalDateTime.now(),

                viewCount = 150,
                commentCount = 5,
                isVerified = true,
                postId = 1, providerId = "1234", updatedAt = LocalDateTime.now()
            ),
        ) { }
    }
}
