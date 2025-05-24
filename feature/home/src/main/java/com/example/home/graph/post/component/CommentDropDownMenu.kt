package com.example.home.graph.post.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.component.CheckCancelDialog
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White

@Composable
internal fun OwnCommentDropdownMenu(
    expanded: Boolean,
    commentId: Int,
    onDismiss: () -> Unit,
    onReply: () -> Unit,
    onDelete: (Int) -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        CheckCancelDialog(
            onCheck = {
                onDelete(commentId)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false },
            dialogText = "정말 삭제하시겠습니까?"
        )
    }

    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .shadow(elevation = 1.dp, RoundedCornerShape(8.dp))
                .background(White),
        ) {
            Row(
                modifier = Modifier
                    .clickable(isRipple = true) {
                        onDismiss()
                        onReply()
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.reply),
                    contentDescription = "답글 달기",
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.reply), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }

            Row(
                modifier = Modifier
                    .clickable(isRipple = true) {
                        onDismiss()
                        showDeleteDialog = true
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.delete_ic),
                    contentDescription = "삭제하기",
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.delete), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }

        }
    }

}

@Composable
internal fun OtherCommentDropdownMenu(
    commentId: Int,
    expanded: Boolean,
    onDismiss: () -> Unit,
    onReply: () -> Unit,
    onReport: (Int) -> Unit,
) {
    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .shadow(elevation = 1.dp, RoundedCornerShape(8.dp))
                .background(White),
        ) {
            Row(
                modifier = Modifier
                    .clickable(isRipple = true) {
                        onDismiss()
                        onReply()
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.reply),
                    contentDescription = "답글 달기",
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.reply), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }

            Row(
                modifier = Modifier
                    .clickable(isRipple = true) {
                        onDismiss()
                        onReport(commentId)
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.report_ic),
                    contentDescription = "신고하기",
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.report), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }
        }
    }
}

@Composable
internal fun OwnChildCommentDropdownMenu(
    expanded: Boolean,
    commentId: Int,
    onDismiss: () -> Unit,
    onDelete: (Int) -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        CheckCancelDialog(
            onCheck = {
                onDelete(commentId)
                showDeleteDialog = false
            },
            onDismiss = { showDeleteDialog = false },
            dialogText = "정말 삭제하시겠습니까?"
        )
    }

    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .background(White),
        ) {
            Row(
                modifier = Modifier
                    .clickable(isRipple = true) {
                        onDismiss()
                        showDeleteDialog = true
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.delete_ic),
                    contentDescription = "삭제하기",
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.delete), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }

        }
    }
}

@Composable
internal fun OtherChildCommentDropdownMenu(
    commentId: Int,
    expanded: Boolean,
    onDismiss: () -> Unit,
    onReport: (Int) -> Unit,
) {
    if (expanded) {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .background(White),
        ) {
            Row(
                modifier = Modifier
                    .clickable(isRipple = true) {
                        onDismiss()
                        onReport(commentId)
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.report_ic),
                    contentDescription = "신고하기",
                    modifier = Modifier.size(24.dp)
                )

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.report), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }
        }
    }
}
