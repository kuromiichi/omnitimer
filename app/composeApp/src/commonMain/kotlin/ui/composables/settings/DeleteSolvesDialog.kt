package ui.composables.settings

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DeleteSolvesDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    if (isOpen) {
        AlertDialog(
            onDismissRequest = {},
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text(text = "Cancel")
                }
            },
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text(text = "Delete all solves")
                }
            },
            title = { Text(text = "Delete all solves") },
            text = {
                Text(
                    text = "Are you sure you want to delete all solves? This action is permanent."
                )
            },
            modifier = modifier
        )
    }
}
