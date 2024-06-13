package dev.kuromiichi.omnitimer.ui.composables.timer

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun TimerArea(
    time: String,
    fontSize: TextUnit,
    isAlerting: Boolean,
    onSurfaceClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = if (isAlerting) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.surfaceVariant,
        shadowElevation = 4.dp,
        onClick = { onSurfaceClick() },
        modifier = modifier
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontFeatureSettings = "tnum")) {
                        append(time)
                    }
                },
                fontSize = fontSize,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}
