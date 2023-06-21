package com.example.newsdemoproject.domain.model

data class ButtonData(
    val text: String?,
    val action: () -> Unit?
)
