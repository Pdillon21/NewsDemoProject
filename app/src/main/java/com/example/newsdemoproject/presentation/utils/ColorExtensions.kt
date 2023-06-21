package com.example.newsdemoproject.presentation.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import java.lang.Exception

fun Color.getOposedColor(): Color {
    val newRed = 1f - this.red
    val newBlue = 1f - this.blue
    val newGreen = 1f - this.green
    return Color(newRed, newBlue, newGreen, alpha = 1f)
}

fun Color.getContrastingScheme(): ContrastingColors {
    // Luminance gives a value from 0 (close to black) to 1 (close to white)
    val optimalLuminanceDifference = 4.5
    val oposedColor = this.getOposedColor()
    val baseLuminance = this.luminance()
    val oposedLuminance = oposedColor.luminance()
    val currentFactor = oposedLuminance / baseLuminance
    if (currentFactor >= optimalLuminanceDifference) {
        return ContrastingColors(
            baseColor = this,
            contrastColor = oposedColor
        )
    } else {
        val luminanceDifferences = optimalLuminanceDifference - currentFactor
        val remainingLuminanceOpposedColor = 1f - oposedLuminance
        if ((remainingLuminanceOpposedColor) > (luminanceDifferences)) {
            // There is enough room in the oposed color to lighten it up without affecting the base color
            return ContrastingColors(
                this,
                contrastColor = oposedColor.increaseLuminance(luminanceDifferences.toFloat())
            )
        } else {
            return ContrastingColors(
                contrastColor = oposedColor.increaseLuminance(luminanceDifferences.toFloat() / 10),
                baseColor = this.reduceLuminance(luminanceDifferences.toFloat() / 10)
            )
        }
    }
}

fun Color.increaseLuminance(factor: Float): Color {
    var newRed = this.red + factor
    var newBlue = this.blue + factor
    var newGreen = this.green + factor
    if (newRed > 1f) {
        newRed = 1f
    }
    if (newBlue > 1f) {
        newBlue = 1f
    }
    if (newGreen > 1f) {
        newGreen = 1f
    }
    return Color(newRed, newBlue, newGreen, alpha = 1f)
}

fun Color.reduceLuminance(factor: Float): Color {
    var newRed = this.red - factor
    var newBlue = this.blue - factor
    var newGreen = this.green - factor
    if (newRed < 0f) {
        newRed = 0f
    }
    if (newBlue < 0f) {
        newBlue = 0f
    }
    if (newGreen < 0f) {
        newGreen = 0f
    }
    return Color(newRed, newBlue, newGreen, alpha = 1f)
}
