package ui.composables

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ProgressIndicatorDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * PROJECT : weather
 * DATE    : Sun 04/02/2024
 * TIME    : 01:11
 * USER    : mambo
 */

@Composable
fun CircularProgressBar(
    arcColor: Color = MaterialTheme.colors.primary,
    modifier: Modifier = Modifier,
    circleColor: Color = Color.LightGray,
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth,
    size: Dp = 32.dp
) {
    Indicator(
        modifier = modifier,
        arcColor = arcColor,
        circleColor = circleColor,
        strokeWidth = strokeWidth,
        size = size
    )
}


@Composable
private fun Indicator(
    size: Dp = 32.dp, // indicator size
    sweepAngle: Float = 80f, // angle (length) of indicator arc
    arcColor: Color = MaterialTheme.colors.primary, // color of indicator arc line
    circleColor: Color = Color.LightGray,// color of the background circle
    strokeWidth: Dp = ProgressIndicatorDefaults.StrokeWidth + 5.dp, //width of circle and ar lines
    modifier: Modifier = Modifier
) {

    val transition = rememberInfiniteTransition()
    val currentArcStarting by transition.animateValue(
        initialValue = 0,
        targetValue = 360,
        typeConverter = Int.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 770,
                easing = LinearEasing
            )
        )
    )

    val stroke = with(LocalDensity.current){
        Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Square)
    }

    Canvas(modifier = modifier
        .size(size)
        .padding(strokeWidth / 2)){
        drawCircle(circleColor, style = stroke)
        drawArc(
            color= arcColor,
            startAngle = currentArcStarting.toFloat()-90,
            sweepAngle = sweepAngle,
            useCenter = false,
            style = stroke
        )

    }

}
