package com.example.newsdemoproject.presentation.screens.main

import com.example.newsdemoproject.data.remote.dto.single_item.GuardianSingleItemDto
import com.example.newsdemoproject.domain.model.ErrorData

sealed class MainScreenUistate() {
    data class Success(val data: List<GuardianSingleItemDto>) : MainScreenUistate()
    data class Error(val errorData: ErrorData) : MainScreenUistate()
    object Loading : MainScreenUistate()

    companion object {
        fun emptyDataError(): Error {
            // ToDo pasar a string resource
            return Error(
                ErrorData(
                    errorTitle = "Sorry!",
                    errorMessage = "We couldn't fetch any story!"
                )
            )
        }

        fun errorWithMessage(errorMessage: String?): Error {
            return Error(
                ErrorData(
                    errorTitle = "Sorry!",
                    errorMessage = errorMessage ?: "We encountered some issues"
                )
            )
        }
    }
}
