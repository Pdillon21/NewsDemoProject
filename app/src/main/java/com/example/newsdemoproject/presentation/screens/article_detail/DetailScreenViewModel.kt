package com.example.newsdemoproject.presentation.screens.article_detail

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsdemoproject.data.remote.common.NewsDemoResource
import com.example.newsdemoproject.data.remote.dto.container.GuardianResponseContainerDto
import com.example.newsdemoproject.domain.model.ErrorData
import com.example.newsdemoproject.domain.use_case.GetSingleArticleUseCase
import kotlinx.coroutines.launch

class DetailScreenViewModel(
    private val getSingleArticleUseCase: GetSingleArticleUseCase = GetSingleArticleUseCase(),
    private val articleId: String = ""
) : ViewModel() {
    private val _uiState = mutableStateOf(DetailScreenUiState.Loading as DetailScreenUiState)
    val uiState: State<DetailScreenUiState> = _uiState

    init {
        getSingleArticle()
    }

    fun refresh() {
        getSingleArticle()
    }

    private fun getSingleArticle() {
        viewModelScope.launch {
            getSingleArticleUseCase.invoke(articleId).collect() { resourceResponse ->
                mapResourceToUiState(resourceResponse)
            }
        }
    }

    private fun mapResourceToUiState(resource: NewsDemoResource<GuardianResponseContainerDto>) {
        this.viewModelScope.launch {
            _uiState.value = when (resource) {
                is NewsDemoResource.Success -> validateSucces(resource.data)
                is NewsDemoResource.Loading -> DetailScreenUiState.Loading
                is NewsDemoResource.Error -> validateError(resource.message)
            }
        }
    }

    private fun validateError(message: String?): DetailScreenUiState {
        return DetailScreenUiState.Error(
            ErrorData(
                errorTitle = "Sorry!",
                errorMessage = message ?: "Somethign went wrong while getting your article"
            )
        )
    }

    private fun validateSucces(data: GuardianResponseContainerDto?): DetailScreenUiState {
        val article = if ((data?.response?.results?.size ?: 0) != 1) {
            null
        } else {
            data?.response?.results?.get(0)
        }
        return if (article != null) {
            DetailScreenUiState.Success(article)
        } else {
            DetailScreenUiState.Error(
                ErrorData(
                    errorTitle = "Sorry!",
                    errorMessage = "Somethign went wrong while getting your article"
                )
            )
        }

    }


}