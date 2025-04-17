package com.example.home.graph.post.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.WarmGray
import com.example.domain.model.home.Comment

@Composable
internal fun CommentView(
    modifier: Modifier = Modifier,
    userId: String = "",
    comment: Comment,
) {
    var isOwnCommentDropDownMenuExpanded by remember { mutableStateOf(false) }
    var isOtherCommentDropDownMenuExpanded by remember { mutableStateOf(false) }

    val profileImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(comment.profileImageUrl ?: R.drawable.default_profile)
            .crossfade(true)
            .build()
    )

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = profileImage,
            contentDescription = "프로필 이미지",
            modifier = modifier
                .size(25.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier.width(6.dp))

        Text(comment.nickName, style = TraceTheme.typography.bodySSB)

        Spacer(modifier.width(6.dp))

        Text(comment.getFormattedTime(), style = TraceTheme.typography.bodyXSM, color = DarkGray)

        if (userId.isEmpty()) {
            Spacer(modifier.weight(1f))

            Box() {
                Image(
                    painter = painterResource(R.drawable.menu_ic),
                    contentDescription = "댓글 메뉴",
                    colorFilter = ColorFilter.tint(WarmGray),
                    modifier = modifier
                        .height(15.dp)
                        .clickable {
                            if(userId.isEmpty()) {
                                isOwnCommentDropDownMenuExpanded = true
                            }
                            else {
                                isOtherCommentDropDownMenuExpanded = true
                            }
                        }
                )

                OwnCommentDropdownMenu(
                    expanded = isOwnCommentDropDownMenuExpanded,
                    onDismiss = { isOwnCommentDropDownMenuExpanded = false },
                    onEdit = {},
                    onDelete = {}
                )

                OtherCommentDropdownMenu(
                    expanded = isOtherCommentDropDownMenuExpanded,
                    onDismiss = { isOtherCommentDropDownMenuExpanded = false },
                    onReport = {}
                )
            }
        }

    }

    Spacer(modifier.height(7.dp))

    Text(comment.content, style = TraceTheme.typography.bodyMM)
}

