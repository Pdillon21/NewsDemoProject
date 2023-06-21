package com.example.newsdemoproject.presentation.utils

import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.positionInWindow

fun LayoutCoordinates.getViewPositionDataContainer(): ViewPositionDataContainer {
    return ViewPositionDataContainer(
        x = this.positionInWindow().x,
        y = this.positionInWindow().y,
        height = this.size.height,
        width = this.size.width
    )
}
