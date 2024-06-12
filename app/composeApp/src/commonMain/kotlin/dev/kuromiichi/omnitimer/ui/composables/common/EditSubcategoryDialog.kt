package dev.kuromiichi.omnitimer.ui.composables.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditSubcategoryDialog(
    modifier: Modifier = Modifier,
    isOpen: Boolean,
    isEdit: Boolean = false,
    title: String,
    subcategory: String,
    onSubcategoryChange: (String) -> Unit,
    onDelete: () -> Unit = {},
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {

    if (isOpen) {
        AlertDialog(
            onDismissRequest = onDismiss,
            confirmButton = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (isEdit) {
                        Button(onClick = onDelete) {
                            Text(text = "Delete")
                        }
                    }
                    Button(onClick = onConfirm) {
                        Text(text = "Confirm")
                    }
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

