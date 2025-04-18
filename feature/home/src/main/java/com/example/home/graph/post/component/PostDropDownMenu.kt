package com.example.home.graph.post.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.common.util.clickable
import com.example.designsystem.R
import com.example.designsystem.component.CheckCancleDialog
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White

/**
 * Displays a dropdown menu for actions on the user's own post, providing "Edit" and "Delete" options.
 *
 * When "Edit" is selected, the menu is dismissed and the edit callback is triggered. Selecting "Delete" opens a confirmation dialog; if confirmed, the delete callback is invoked.
 */
@Composable
internal fun OwnPostDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        CheckCancleDialog(
            onCheck = {
                onDelete()
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
                        onEdit()
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = painterResource(R.drawable.edit_ic), contentDescription = "신고하기")

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.edit), style = TraceTheme.typography.bodyMR)

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
                Image(painter = painterResource(R.drawable.delete_ic), contentDescription = "삭제하기")

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.delete), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }

        }
    }

}

/**
 * Displays a dropdown menu with a "Report" option for posts not owned by the user.
 *
 * When expanded, shows a single menu item that triggers the report action and dismisses the menu when selected.
 */
@Composable
internal fun OtherPostDropdownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onReport: () -> Unit,
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
                        onReport()
                    }
                    .padding(top = 15.dp, bottom = 15.dp, start = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(painter = painterResource(R.drawable.report_ic), contentDescription = "신고하기")

                Spacer(Modifier.width(12.dp))

                Text(stringResource(R.string.report), style = TraceTheme.typography.bodyMR)

                Spacer(Modifier.width(70.dp))
            }
        }
    }
}

