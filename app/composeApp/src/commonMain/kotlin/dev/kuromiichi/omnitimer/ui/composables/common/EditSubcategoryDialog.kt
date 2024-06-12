package dev.kuromiichi.omnitimer.ui.composables.common

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun EditSubcategoryDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    title: String,
    subcategory: String,
    onSubcategoryChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Button(onClick = onConfirm) {
                    Text(text = "Confirm")
                }
            },
            title = {
                Text(text = title)
            },
            text = {
                TextField(value = subcategory, onValueChange = onSubcategoryChange)
            },
            modifier = modifier
        )
    }
}

