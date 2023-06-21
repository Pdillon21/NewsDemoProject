package com.example.newsdemoproject.presentation.screens.article_detail

import android.graphics.drawable.Drawable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.newsdemoproject.data.remote.dto.single_item.GuardianSingleItemDto
import com.example.newsdemoproject.domain.model.ErrorData
import com.example.newsdemoproject.presentation.utils.ContrastingColors
import com.example.newsdemoproject.presentation.utils.getContrastingScheme
import com.example.newsdemoproject.presentation.views.components.NewsProjectLoading
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

@Composable
fun DetailScreen(averageColor: Color = Color.Black, articleApiUrl: String?) {
    val viewModel = remember {
        DetailScreenViewModel(articleId = articleApiUrl ?: "")
    }
    val uiState by viewModel.uiState

    var contrastingColor by remember {
        mutableStateOf(null as ContrastingColors?)
    }
    LaunchedEffect(Unit) {
        this.launch(IO) {
            contrastingColor = averageColor.getContrastingScheme()
        }
    }
    when (uiState) {
        is DetailScreenUiState.Error ->
            DetailScreenError(
                errorData = (uiState as DetailScreenUiState.Error).errorData,
                baseColor = averageColor
            ) {
                viewModel.refresh()
            }

        is DetailScreenUiState.Loading ->
            DetailsScreenEmptyLoading(contrastingColors = contrastingColor)


        is DetailScreenUiState.Success ->
            DetailScreenSuccessView(
                (uiState as DetailScreenUiState.Success).data,
                contrastingColors = contrastingColor,
                baseColor = averageColor
            ) {

            }

    }
}

@Composable
fun DetailScreenSuccessView(
    data: GuardianSingleItemDto,
    baseColor: Color,
    contrastingColors: ContrastingColors?,
    onRefreshRequest: () -> Unit
) {
    val asyncImagePainter = rememberAsyncImagePainter(model = data.fields.thumbnail)
    when (asyncImagePainter.state) {
        is AsyncImagePainter.State.Loading -> DetailsScreenEmptyLoading(contrastingColors = contrastingColors)
        is AsyncImagePainter.State.Error -> DetailScreenContent(
            contrastingColors = contrastingColors,
            drawable = null,
            article = data
        )

        is AsyncImagePainter.State.Success -> DetailScreenContent(
            contrastingColors = contrastingColors,
            drawable = (asyncImagePainter.state as AsyncImagePainter.State.Success).result.drawable,
            article = data
        )

        else -> DetailScreenError(
            errorData = ErrorData(
                errorTitle = "Sorry",
                errorMessage = "Something went wrong while fetching your article."
            ),
            baseColor = baseColor
        ) {
            onRefreshRequest()
        }
    }
}

@Composable
fun DetailScreenError(errorData: ErrorData, baseColor: Color, onItemClick: () -> Unit) {

}

@Composable
fun DetailScreenContent(
    contrastingColors: ContrastingColors?,
    drawable: Drawable?,
    article: GuardianSingleItemDto
) {

}

@Composable
fun DetailsScreenEmptyLoading(contrastingColors: ContrastingColors?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(contrastingColors?.baseColor ?: Color.Black),
        contentAlignment = Alignment.Center
    ) {
        NewsProjectLoading(color = contrastingColors?.contrastColor ?: Color.White)
    }
}
