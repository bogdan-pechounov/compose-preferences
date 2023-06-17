package com.uzential.compose_preferences.ui.components.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.uzential.compose_preferences.ui.ComposeFunction

data class DialogInfo(val title: String, val confirmText: String, val cancelText: String)

@Composable
fun SimpleDialog(
    dialogInfo: DialogInfo,
    open: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: ComposeFunction
) {
    if (!open) return

    AlertDialog(
        title = {
            Text(dialogInfo.title)
        },
        text = {
            content()
        },
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = dialogInfo.confirmText, style = MaterialTheme.typography.labelLarge)
            }
        }, dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dialogInfo.cancelText, style = MaterialTheme.typography.labelLarge)
            }
        })
}