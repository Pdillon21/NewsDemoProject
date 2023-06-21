package com.example.newsdemoproject.presentation.screens.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.newsdemoproject.domain.model.ButtonData
import com.example.newsdemoproject.presentation.EventRequest
import com.example.newsdemoproject.presentation.views.ErrorScreen
import com.example.newsdemoproject.presentation.views.LoadingView
import com.example.newsdemoproject.presentation.views.articles.NewsList
import com.example.newsdemoproject.utils.log.NewsDemoProjectLogger

@Composable
fun MainScreen() {
    val viewModel = remember {
        MainViewModel()
    }
    val screenState by viewModel.uiState
    MainScreenInitialContainer(screenState) {
        when (it) {
            is EventRequest.IemClick -> {
                NewsDemoProjectLogger.log("UiElementDebug", "itemClick")
            }

            is EventRequest.RefreshEvent -> {
                viewModel.refresh()
            }
        }
    }
}

@Composable
fun MainScreenInitialContainer(
    screenState: MainScreenUistate,
    requestEvent: (EventRequest) -> Unit
) {
    when (screenState) {
        is MainScreenUistate.Success -> NewsList(
            modifier = Modifier.fillMaxSize(),
            list = screenState.data,
            lazyEnabled = false,
            enableSwipeToRefresh = true
        ) {
            requestEvent(it)
        }

        is MainScreenUistate.Loading -> LoadingView(modifier = Modifier.fillMaxSize())
        is MainScreenUistate.Error -> ErrorScreen(
            modifier = Modifier.fillMaxSize(),
            screenState.errorData,
            ButtonData("Try again") {
                requestEvent(EventRequest.RefreshEvent)
            }
        )
    }
}
