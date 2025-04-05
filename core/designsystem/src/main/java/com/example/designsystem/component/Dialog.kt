package com.example.designsystem.component

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.designsystem.R
import com.example.designsystem.theme.primaryDefault

@Composable
fun checkDialog(
    onDismiss: () -> Unit, dialogText: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.dialog_confirm), color = primaryDefault)
            }
        }
    )
}

@Composable
fun checkCancleDialog(
    onCheck: () -> Unit, onDismiss: () -> Unit, dialogText: String
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        text = { Text(dialogText) },
        confirmButton = {
            TextButton(onClick = { onCheck() }) {
                Text(stringResource(R.string.dialog_confirm), color = primaryDefault)
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(stringResource(R.string.dialog_cancel), color = primaryDefault)
            }
        }
    )
}