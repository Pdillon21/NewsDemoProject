package com.example.newsdemoproject.presentation.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.newsdemoproject.domain.model.ButtonData
import com.example.newsdemoproject.domain.model.ErrorData
import com.example.newsdemoproject.presentation.views.components.NewsDemoButton

@Composable
fun ErrorScreen(
    modifier: Modifier,
    errorData: ErrorData,
    button: ButtonData?
) {
    Box(
        modifier = modifier, contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = errorData.errorTitle ?: "Upsi daisy")
            Text(text = errorData.errorMessage ?: "There was an issue. Try later")
            button?.let {
                NewsDemoButton(
                    buttonData = button, modifier = Modifier
                        .wrapContentWidth()
                        .wrapContentHeight()
                )
            }
        }
    }
}
