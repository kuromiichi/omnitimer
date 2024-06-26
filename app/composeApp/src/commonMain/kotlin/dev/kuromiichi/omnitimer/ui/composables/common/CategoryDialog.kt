package dev.kuromiichi.omnitimer.ui.composables.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CategoryDialog(
    modifier: Modifier = Modifier,
    elements: List<String>,
    title: String,
    isEditable: Boolean = false,
    onClick: (String) -> Unit,
    onEditClick: (String) -> Unit = {},
    onDismiss: () -> Unit,
    confirmButton: @Composable () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = confirmButton,
        title = {
            Text(text = title)
        },
        text = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                elements.forEach { element ->
                    Surface(
                        modifier = Modifier.fillMaxWidth()
                            .clickable(onClick = { onClick(element) }),
                        color = MaterialTheme.colorScheme.secondary,
                        shape = MaterialTheme.shapes.small,
                        shadowElevation = 4.dp
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier.weight(1f),
                                text = element,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (isEditable) {
                                IconButton(onClick = { onEditClick(element) }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit subcategory"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        },
        modifier = modifier
    )
}
