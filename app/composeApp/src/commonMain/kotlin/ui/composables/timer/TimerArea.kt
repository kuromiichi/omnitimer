package ui.composables.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TimerArea(
    time: String,
    onSurfaceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 4.dp,
        onClick = { onSurfaceClick() },
        modifier = modifier
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = time,
                fontSize = 48.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
