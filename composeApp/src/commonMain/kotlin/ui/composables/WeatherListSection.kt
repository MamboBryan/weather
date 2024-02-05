package ui.composables

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.models.WeatherForecastData
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.helpers.LoadState

/**
 * PROJECT : weather
 * DATE    : Mon 05/02/2024
 * TIME    : 01:15
 * USER    : mambo
 */

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WeatherListSection(
    title: String,
    state: LoadState<List<WeatherForecastData>>,
    modifier: Modifier = Modifier,
    isReversed: Boolean = false,
    onClickRetry: () -> Unit = {},
    onClickItem: (WeatherForecastData) -> Unit = {},
) {
    Column(modifier = modifier) {
        Text(
            modifier = Modifier.background(Color.Gray.copy(alpha = 0.1f)).fillMaxWidth()
                .padding(24.dp),
            text = title
        )
        AnimatedContent(state) { value ->
            when (value) {
                LoadState.Loading -> {
                    CenteredColumn(modifier = Modifier.fillMaxWidth()) {
                        CircularProgressBar(modifier = Modifier.padding(48.dp))
                    }
                }

                is LoadState.Error -> {
                    CenteredColumn(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(48.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            val painter = painterResource("images/error.jpg")
                            Image(
                                modifier = Modifier.padding(vertical = 8.dp).size(48.dp),
                                painter = painter,
                                contentDescription = null
                            )
                            Text(text = value.message)
                            TextButton(
                                modifier = Modifier.padding(top = 8.dp),
                                onClick = onClickRetry
                            ) {
                                Text(text = "retry")
                                Icon(
                                    modifier = Modifier.padding(start = 4.dp).size(16.dp),
                                    imageVector = Icons.Rounded.Refresh,
                                    contentDescription = null
                                )
                            }
                        }
                    }
                }

                is LoadState.Success -> {
                    LazyColumn(modifier = Modifier, reverseLayout = isReversed) {
                        items(value.data.size) { index ->
                            val data = value.data[index]
                            Column(
                                modifier = Modifier.clickable { onClickItem.invoke(data) }
                                    .fillMaxWidth().padding(8.dp)
                            ) {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.width(100.dp)) {
                                            Text(
                                                text = data.date.dayOfWeek.name.lowercase()
                                                    .replaceFirstChar { it.uppercase() })
                                            Text(
                                                text = "${data.date.dayOfMonth} ${
                                                    data.date.month.name.lowercase()
                                                        .replaceFirstChar { it.uppercase() }
                                                        .subSequence(0, 3)
                                                }"
                                            )
                                        }

                                        val painter =
                                            asyncPainterResource(data = "https:" + data.day.condition.iconUrl)
                                        KamelImage(
                                            modifier = Modifier.size(48.dp),
                                            resource = painter,
                                            contentScale = ContentScale.Crop,
                                            contentDescription = "Profile",
                                        )

                                        Text(
                                            text = buildAnnotatedString {
                                                withStyle(
                                                    style = SpanStyle(
                                                        fontWeight = FontWeight.Bold,
                                                    )
                                                ) {
                                                    append("${data.day.maxTemperatureInCelsius.toInt()}")
                                                }
                                                withStyle(
                                                    style = SpanStyle(
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 12.sp,
                                                        baselineShift = BaselineShift.Superscript
                                                    )
                                                ) {
                                                    append("o")
                                                }
                                                append(" - ")
                                                append("${data.day.minTemperatureInCelsius.toInt()}")
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

                                        Text(
                                            modifier = Modifier.width(100.dp),
                                            text = data.day.condition.label,
                                            overflow = TextOverflow.Ellipsis,
                                            textAlign = TextAlign.End,
                                            maxLines = 1
                                        )
                                    }
                                    if (index != value.data.lastIndex) {
                                        Divider(
                                            modifier = Modifier.fillMaxWidth(),
                                            color = MaterialTheme.colors.onBackground.copy(0.1f)
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }
}