package ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.extensions.as24HourTime
import data.models.WeatherHourData

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 02:48
 * USER    : mambo
 */

@Composable
fun WeatherHourItem(
    item: WeatherHourData,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = buildAnnotatedString {
                append("${item.temperatureInCelsius}")
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        baselineShift = BaselineShift.Superscript
                    )
                ) {
                    append("o")
                }
            }
        )
        Column(
            modifier = Modifier.size(50.dp)
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(25))
                .background(MaterialTheme.colors.primary)
        ) { }
        Text(text = item.time.as24HourTime())
    }
}