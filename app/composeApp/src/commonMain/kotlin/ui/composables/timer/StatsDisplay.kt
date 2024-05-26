package ui.composables.timer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun StatsDisplay(
    stats: Map<String, String>,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.tertiary,
        shadowElevation = 4.dp,
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            stats.forEach {
                Row {
                    Text(
                        text = it.key,
                        modifier = Modifier.weight(1f)
                    )
                    Text(text = it.value)
                }
            }
        }
    }
}
