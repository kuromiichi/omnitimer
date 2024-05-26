package ui.composables.timer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ScrambleImage(
    image: DrawableResource,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxHeight(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.tertiary,
        shadowElevation = 4.dp
    ) {
        Image(
            painter = painterResource(resource = image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        )
    }
}
