package com.example.newsdemoproject.presentation.screens.article_detail

import com.example.newsdemoproject.data.remote.dto.single_item.GuardianSingleItemDto
import com.example.newsdemoproject.domain.model.ErrorData

sealed class DetailScreenUiState {
    data class Success(val data: GuardianSingleItemDto) :
        DetailScreenUiState()

    data class Error(val errorData: ErrorData) : DetailScreenUiState()
    object Loading : DetailScreenUiState()
}
