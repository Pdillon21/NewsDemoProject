package com.example.newsdemoproject.presentation.utils

import android.graphics.Bitmap
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

fun Bitmap.getAverageColor(): androidx.compose.ui.graphics.Color {
    var redBucket: Long = 0
    var greenBucket: Long = 0
    var blueBucket: Long = 0
    var pixelCount: Long = 0

    for (y in 0 until this.height) {
        for (x in 0 until this.width) {
            val c: Int = this.getPixel(x, y)
            pixelCount++
            redBucket += c.red
            greenBucket += c.green
            blueBucket += c.blue
        }
    }
    val averageRed = redBucket / pixelCount
    val averageGreen = greenBucket / pixelCount
    val averageBlue = blueBucket / pixelCount

    return androidx.compose.ui.graphics.Color(
        averageRed.toInt(),
        averageBlue.toInt(),
        averageGreen.toInt(),
        255
    )
}
