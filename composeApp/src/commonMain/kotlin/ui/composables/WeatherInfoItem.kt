package ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 02:24
 * USER    : mambo
 */

@Composable
fun WeatherInfoItem(
    title: String,
    value: String,
    unit: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            fontWeight = FontWeight.Light
        )
        Row {
            Text(
                text = value,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            Text(text = unit)
        }
    }
}