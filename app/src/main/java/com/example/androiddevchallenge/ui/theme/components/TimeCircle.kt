package com.example.androiddevchallenge.ui.theme.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.intimesimple.ui.theme.Green500
import com.example.intimesimple.utils.calculateRadiusOffset
import kotlin.math.min

@Composable
fun TimerCircleComponent(
    modifier: Modifier = Modifier,
    screenWidthDp: Int,
    screenHeightDp: Int,
    time: String,
    state: String,
    reps: String,
    elapsedTime: Long,
    totalTime: Long
){
    val maxRadius by remember { mutableStateOf(min(screenHeightDp, screenWidthDp)) }

    Box(
        modifier = modifier.size(maxRadius.dp).padding(8.dp)
    ){


        Column(modifier = Modifier.fillMaxSize()) {

            Text(
                modifier = Modifier.layoutId("timerText"),
                text = time,
                style = typography.h2,
            )

            Text(
                modifier = Modifier.layoutId("workoutStateText"),
                text = state,
                style = typography.h5,
            )

            Text(
                modifier = Modifier.layoutId("repText"),
                text = reps,
                style = typography.h5,
            )
        }

        // only show in portrait mode
        if(screenWidthDp.dp < 600.dp){
            TimerCircle(
                modifier = modifier,
                elapsedTime = elapsedTime,
                totalTime = totalTime
            )
        }
    }
}

@Composable
fun TimerCircle(
    modifier: Modifier = Modifier,
    elapsedTime: Long,
    totalTime: Long
){
    Canvas(modifier = modifier.fillMaxSize(), onDraw = {
        val height = size.height
        val width = size.width
        val dotDiameter = 12.dp
        val strokeSize = 20.dp
        val radiusOffset
                = calculateRadiusOffset(strokeSize.value, dotDiameter.value, 0f)

        val xCenter = width/2f
        val yCenter = height/2f
        val radius = min(xCenter, yCenter)
        val arcWidthHeight = ((radius - radiusOffset) * 2f)
        val arcSize = Size(arcWidthHeight, arcWidthHeight)

        val remainderColor = Color.White.copy(alpha = 0.25f)
        val completedColor = Green500

        val whitePercent =
            min(1f, elapsedTime.toFloat()/totalTime.toFloat())
        val greenPercent = 1 - whitePercent

        drawArc(
            completedColor,
            270f,
            -greenPercent * 360f,
            false,
            topLeft = Offset(radiusOffset, radiusOffset),
            size = arcSize,
            style = Stroke(width = strokeSize.value)
        )

        drawArc(
            remainderColor,
            270f,
            whitePercent*360,
            false,
            topLeft = Offset(radiusOffset, radiusOffset),
            size = arcSize,
            style = Stroke(width = strokeSize.value)
        )

    })
}

@Composable
fun DebugCenterLines(
    modifier: Modifier
){
    Canvas(modifier = modifier.fillMaxSize(), onDraw = {
        drawLine(
            color = Color.Black,
            start = Offset(size.width/2f, 0f),
            end = Offset(size.width/2f, size.height),
            strokeWidth = 4f
        )

        drawLine(
            color = Color.Black,
            start = Offset(0f, size.height/2f),
            end = Offset(size.width, size.height/2f),
            strokeWidth = 4f
        )
    })
}
