package dev.kuromiichi.omnitimer.ui.composables.list

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.kuromiichi.omnitimer.data.models.Status
import dev.kuromiichi.omnitimer.platform.toImageBitmap

@Composable
fun ListContent(
    modifier: Modifier = Modifier.fillMaxWidth(),
    scramble: String,
    image: String,
    selectedPenalty: Status,
    isPortrait: Boolean,
    onPenaltySelected: (Status) -> Unit,
    onDeleteClick: () -> Unit,
    onArchiveClick: () -> Unit,
) {
    when (isPortrait) {
        true -> ContentPortrait(
            modifier,
            scramble,
            image,
            selectedPenalty,
            onPenaltySelected,
            onDeleteClick,
            onArchiveClick
        )

        false -> ContentLandscape(
            modifier,
            image,
            scramble,
            selectedPenalty,
            onPenaltySelected,
            onDeleteClick,
            onArchiveClick
        )
    }
}

@Composable
private fun ContentLandscape(
    modifier: Modifier,
    image: String,
    scramble: String,
    selectedPenalty: Status,
    onPenaltySelected: (Status) -> Unit,
    onDeleteClick: () -> Unit,
    onArchiveClick: () -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row {
            Image(
                modifier = Modifier.weight(1f),
                bitmap = image.toImageBitmap(),
                contentDescription = null
            )
            Text(
                modifier = Modifier.weight(1f),
                text = scramble,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
        }

        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            shadowElevation = 4.dp,
            shape = MaterialTheme.shapes.small
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
                            contentDescription = "Delete solve",
                        )
                    }
                }

                Surface(
                    shape = MaterialTheme.shapes.medium,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shadowElevation = 4.dp,
                ) {
                    IconButton(onClick = { onArchiveClick() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Archive solve",
                        )
                    }
                }
            }
        }

    }
}

@Composable
fun ContentPortrait(
    modifier: Modifier,
    scramble: String,
    image: String,
    selectedPenalty: Status,
    onPenaltySelected: (Status) -> Unit,
    onDeleteClick: () -> Unit,
    onArchiveClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.weight(1f),
            bitmap = image.toImageBitmap(),
            contentDescription = null
        )
        Text(
            modifier = Modifier.weight(1f),
            text = scramble,
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center
        )
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.secondary,
            shadowElevation = 4.dp,
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.SpaceEvenly
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
                }
                Surface(
                    modifier = Modifier.fillMaxWidth().height(4.dp).padding(horizontal = 8.dp),
                    shape = MaterialTheme.shapes.small,
                    color = MaterialTheme.colorScheme.secondaryContainer,
                ) {

                }
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shadowElevation = 4.dp,
                    ) {
                        IconButton(onClick = { onDeleteClick() }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete solve",
                            )
                        }
                    }

                    Surface(
                        shape = MaterialTheme.shapes.medium,
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shadowElevation = 4.dp,
                    ) {
                        IconButton(onClick = { onArchiveClick() }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = "Archive solve",
                            )
                        }
                    }
                }
            }
        }
    }
}
