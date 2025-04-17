package com.example.designsystem.component

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.designsystem.R
import com.example.designsystem.theme.DarkGray
import com.example.designsystem.theme.PrimaryDefault
import com.example.designsystem.theme.TraceTheme
import com.example.designsystem.theme.White


@Composable
fun CheckDialog(
    onDismiss: () -> Unit, dialogText: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(dialogText) },
        shape = RoundedCornerShape(8.dp),
        containerColor = White,
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    stringResource(R.string.dialog_confirm),
                    color = PrimaryDefault,
                    style = TraceTheme.typography.bodySM
                )
            }
        }
    )
}

@Composable
fun CheckCancleDialog(
    onCheck: () -> Unit, onDismiss: () -> Unit, dialogText: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(dialogText) },
        shape = RoundedCornerShape(8.dp),
        containerColor = White,
        confirmButton = {
            TextButton(onClick = { onCheck() }) {
                Text(
                    stringResource(R.string.dialog_confirm),
                    color = PrimaryDefault,
                    style = TraceTheme.typography.bodySM
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    stringResource(R.string.dialog_cancel),
                    color = DarkGray,
                    style = TraceTheme.typography.bodySM
                )
            }
        }
    )

}
