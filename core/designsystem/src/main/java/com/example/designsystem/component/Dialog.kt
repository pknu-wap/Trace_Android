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


/**
 * Displays an alert dialog with a single confirm button.
 *
 * The dialog shows the provided text and dismisses when the confirm button is clicked or when the dialog is dismissed by the user.
 *
 * @param onDismiss Callback invoked when the dialog is dismissed or the confirm button is pressed.
 * @param dialogText The message displayed inside the dialog.
 */
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

/**
 * Displays an alert dialog with customizable text, a confirm button, and a cancel button.
 *
 * The confirm button triggers the provided `onCheck` callback, while the cancel button and dialog dismissal trigger `onDismiss`.
 *
 * @param onCheck Callback invoked when the confirm button is clicked.
 * @param onDismiss Callback invoked when the dialog is dismissed or the cancel button is clicked.
 * @param dialogText The message displayed inside the dialog.
 */
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
