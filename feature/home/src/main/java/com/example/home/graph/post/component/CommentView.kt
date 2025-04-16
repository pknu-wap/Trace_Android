package com.example.home.graph.post.component


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.designsystem.R
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.TraceTheme
import com.example.domain.model.home.Comment

@Composable
fun CommentView(
    comment: Comment,
) {
    val profileImage = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(comment.profileImageUrl ?: R.drawable.default_profile)
            .crossfade(true)
            .build()
    )

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = profileImage,
            contentDescription = "프로필 이미지",
            modifier = Modifier
                .size(25.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.width(6.dp))

        Text(comment.nickName, style = TraceTheme.typography.bodySSB)

        Spacer(Modifier.width(6.dp))

        Text(comment.getFormattedTime(), style = TraceTheme.typography.bodyXSM, color = DarkGray)
    }

    Spacer(Modifier.height(7.dp))

    Text(comment.content, style = TraceTheme.typography.bodyMM)
}

