package ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * PROJECT : weather
 * DATE    : Sat 03/02/2024
 * TIME    : 23:13
 * USER    : mambo
 */

@Composable
fun LeftIconWithText(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = "navigate to history",
            tint = MaterialTheme.colors.primary
        )
        TextButton(onClick = onClick) {
            Text(text = text)
        }
    }
}

@Composable
fun RightIconWithText(
    text: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.clickable { onClick.invoke() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(onClick = onClick) {
            Text(text = text)
        }
        Icon(
            imageVector = icon,
            contentDescription = "navigate to history",
            tint = MaterialTheme.colors.primary
        )
    }
}