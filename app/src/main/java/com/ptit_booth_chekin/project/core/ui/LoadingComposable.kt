package com.ptit_booth_chekin.project.core.ui

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


@Preview
@Composable
private fun LoadingComposablePrev() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        RoseCurveSpinner()

    }
}

@Composable
fun RoseCurveSpinner(
    modifier: Modifier = Modifier,
    petals: Int = 2,
    tailLength: Float = .2f,
    color: Color = Color.White,
) {

    val infinite = rememberInfiniteTransition()
    val progress by infinite.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 2_000,
                easing = LinearEasing,
            )
        )
    )
    Box(
        modifier = modifier
            .defaultMinSize(50.dp, 50.dp)
            .drawWithCache {
                val path = createRoseCurve(
                    center = size.center,
                    radius = size.center.x,
                    petals = petals,
                    resolution = 500,
                )
                val measure = PathMeasure()
                measure.setPath(path, true)
                onDrawBehind {

                    val startDistance = measure.length * progress
                    val stopDistance = (measure.length * progress) + (measure.length * tailLength)
                    val segment = Path()
                    measure.getSegment(
                        startDistance = startDistance,
                        stopDistance = stopDistance,
                        destination = segment
                    )

                    val segment2 = Path()
                    if (stopDistance > measure.length) {
                        measure.getSegment(
                            startDistance = 0f,
                            stopDistance = stopDistance - measure.length,
                            destination = segment2
                        )
                    }

                    listOf(segment, segment2).forEach { path ->
                        drawPath(
                            path = path,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    color,
                                    color,
                                    color
                                ),
                            ),
                            style = Stroke(
                                width = 24f,
                                cap = StrokeCap.Round,
                                join = StrokeJoin.Round
                            )
                        )
                    }
                }
            }
    )

}

fun Path.moveTo(offset: Offset) = moveTo(offset.x, offset.y)
fun Path.lineTo(offset: Offset) = lineTo(offset.x, offset.y)


fun Path.polarLineTo(
    degrees: Float,
    distance: Float,
    origin: Offset = Offset.Zero
) {
    lineTo(polarToCart(degrees, distance, origin))
}

fun polarToCart(
    degrees: Float,
    distance: Float,
    origin: Offset = Offset.Zero
): Offset = Offset(
    x = distance * cos(-degrees * (PI / 180)).toFloat(),
    y = distance * sin(-degrees * (PI / 180)).toFloat(),
) + origin

fun createRoseCurve(
    center: Offset,
    radius: Float,
    petals: Int,
    resolution: Int = 300
): Path {
    return Path().apply {
        moveTo(center)
        for (i in 0..resolution) {
            val degrees = (i / resolution.toFloat()) * 360f
            polarLineTo(
                degrees = degrees,
                distance = (radius * sin((degrees * PI / 180) * petals)).toFloat(),
                origin = center
            )
        }
    }
}