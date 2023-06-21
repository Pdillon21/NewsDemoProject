package com.example.newsdemoproject.presentation.screens.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsdemoproject.data.remote.common.NewsDemoResource
import com.example.newsdemoproject.data.remote.dto.container.GuardianResponseContainerDto
import com.example.newsdemoproject.domain.use_case.GetGeneralArticlesUseCase
import kotlinx.coroutines.launch

class MainViewModel(
    private val getGralArticlesUseCase: GetGeneralArticlesUseCase = GetGeneralArticlesUseCase()
) : ViewModel() {

    private val _uiState = mutableStateOf(MainScreenUistate.Loading as MainScreenUistate)
    val uiState: State<MainScreenUistate> = _uiState

    init {
        getGralArticles()
    }

    fun refresh() {
        getGralArticles()
    }

    private fun getGralArticles() {
        viewModelScope.launch {
            getGralArticlesUseCase.invoke().collect() {
                mapResourceToUiState(it)
            }
        }
    }

    private fun mapResourceToUiState(resource: NewsDemoResource<GuardianResponseContainerDto>) {
        this.viewModelScope.launch {
            _uiState.value = when (resource) {
                is NewsDemoResource.Success -> validateSucces(resource.data)
                is NewsDemoResource.Loading -> MainScreenUistate.Loading
                is NewsDemoResource.Error -> validateError(resource.message)
            }
        }
    }

    private fun validateError(data: String?): MainScreenUistate {
        return MainScreenUistate.errorWithMessage(data)
    }

    private fun validateSucces(data: GuardianResponseContainerDto?): MainScreenUistate {
        val results = data?.response?.results
        return if (results.isNullOrEmpty()) {
            MainScreenUistate.emptyDataError()
        } else {
            (MainScreenUistate.Success(results))
        }
    }
}
