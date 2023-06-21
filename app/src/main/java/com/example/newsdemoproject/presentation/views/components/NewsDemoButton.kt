package com.example.newsdemoproject.presentation.views.components

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.newsdemoproject.domain.model.ButtonData

@Composable
fun NewsDemoButton(modifier: Modifier = Modifier, buttonData: ButtonData) {
    Button(
        modifier = modifier,
        onClick = {
            buttonData.action()
        },
        content = {
            Text(text = buttonData.text ?: "Press")
        }
    )
}
