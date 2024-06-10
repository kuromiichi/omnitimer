package dev.kuromiichi.omnitimer.ui.composables.timer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kuromiichi.omnitimer.data.models.Status

@Composable
fun PenaltySelector(
    selectedPenalty: Status,
    onPenaltySelected: (Status) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.secondary,
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedPenalty == Status.OK) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.secondary
                    },
                    contentColor = if (selectedPenalty == Status.OK) {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSecondary
                    }
                ),
                onClick = { onPenaltySelected(Status.OK) }
            ) {
                Text(
                    text = "OK",
                    fontSize = 20.sp
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedPenalty == Status.PLUS_TWO) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.secondary
                    },
                    contentColor = if (selectedPenalty == Status.PLUS_TWO) {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSecondary
                    }
                ),
                onClick = { onPenaltySelected(Status.PLUS_TWO) }
            ) {
                Text(
                    text = "+2",
                    fontSize = 20.sp
                )
            }
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedPenalty == Status.DNF) {
                        MaterialTheme.colorScheme.secondaryContainer
                    } else {
                        MaterialTheme.colorScheme.secondary
                    },
                    contentColor = if (selectedPenalty == Status.DNF) {
                        MaterialTheme.colorScheme.onSecondaryContainer
                    } else {
                        MaterialTheme.colorScheme.onSecondary
                    }
                ),
                onClick = { onPenaltySelected(Status.DNF) }
            ) {
                Text(
                    text = "DNF",
                    fontSize = 20.sp
                )
            }
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.secondaryContainer,
                shadowElevation = 4.dp,
            ) {
                IconButton(onClick = { onDeleteClick() }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete time",
                    )
                }
            }
        }
    }
}
