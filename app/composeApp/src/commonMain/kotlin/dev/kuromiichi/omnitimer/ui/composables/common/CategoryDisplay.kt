package dev.kuromiichi.omnitimer.ui.composables.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun CategoryDisplay(
    modifier: Modifier = Modifier,
    categoryName: String,
    subcategoryName: String,
    onCategoryClick: () -> Unit,
    onSubcategoryClick: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Surface(
            modifier = Modifier.weight(2f).fillMaxHeight()
                .clickable { onCategoryClick() },
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.tertiary,
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = categoryName,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
        Surface(
            modifier = Modifier.weight(3f).fillMaxHeight()
                .clickable { onSubcategoryClick() },
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.tertiary,
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = subcategoryName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}
