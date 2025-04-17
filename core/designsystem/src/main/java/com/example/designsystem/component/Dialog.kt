package com.example.designsystem.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.designsystem.R
import com.example.designsystem.theme.PrimaryDefault


@Composable
fun CheckDialog(
    onDismiss: () -> Unit, dialogText: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.dialog_confirm), color = PrimaryDefault)
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
        confirmButton = {
            TextButton(onClick = { onCheck() }) {
                Text(stringResource(R.string.dialog_confirm), color = PrimaryDefault)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.dialog_cancel), color = PrimaryDefault)
            }
        }
    )

}
