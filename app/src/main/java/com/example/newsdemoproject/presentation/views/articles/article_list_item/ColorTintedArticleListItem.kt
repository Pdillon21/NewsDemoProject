package com.example.newsdemoproject.presentation.views.articles.article_list_item

import android.graphics.drawable.Drawable
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.newsdemoproject.data.remote.dto.single_item.GuardianSingleItemDto
import com.example.newsdemoproject.presentation.utils.ContrastingColors
import com.example.newsdemoproject.presentation.utils.getAverageColor
import com.example.newsdemoproject.presentation.utils.getContrastingScheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ColorTintedArticleListItem(
    modifier: Modifier,
    item: GuardianSingleItemDto
) {
    val painter = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(item.fields.thumbnail)
            .allowHardware(false)
            .size(coil.size.Size.ORIGINAL) // Set the target size to load the image at.
            .build(),
        contentScale = ContentScale.FillBounds
    )
    var painterResult by remember {
        mutableStateOf(null as Drawable?)
    }
    var contrastColors by remember {
        mutableStateOf(null as ContrastingColors?)
    }
    var articleState by remember {
        mutableStateOf(ArticleListItemState.Loading as ArticleListItemState)
    }
    when (painter.state) {
        is AsyncImagePainter.State.Success -> painterResult =
            (painter.state as AsyncImagePainter.State.Success).result.drawable

        is AsyncImagePainter.State.Loading -> articleState = ArticleListItemState.Loading
        else -> articleState = ArticleListItemState.Error
    }
    if (painterResult != null) {
        LaunchedEffect(null) {
            this.launch(Dispatchers.IO) {
                contrastColors = painterResult
                    ?.toBitmap()
                    ?.getAverageColor()
                    ?.getContrastingScheme()
                    ?: ContrastingColors(
                        baseColor = Color.Black,
                        contrastColor = Color.White
                    )
            }
        }
    }

    contrastColors?.let { nonNullContrast ->
        painterResult?.let { nonNullDrawable ->
            articleState = ArticleListItemState.Success(
                contrastingColors = nonNullContrast,
                articleDto = item,
                imageResult = nonNullDrawable
            )
        }
    }

    GetCardByAverageColor(modifier = modifier, articleState)
}

@Composable
fun GetCardByAverageColor(
    modifier: Modifier,
    articleItemState: ArticleListItemState
) {
    Box(
        modifier = modifier
            .animateContentSize()
    ) {
        when (articleItemState) {
            is ArticleListItemState.Loading -> {
                EmptyLoadingCard()
            }

            is ArticleListItemState.Success -> {
                ColorTintedCard(articleItemState)
            }

            else -> {
                ErrorCard()
            }
        }
    }
}

@Composable
fun ColorTintedCard(
    articleItemState: ArticleListItemState.Success
) {
    Card() {
        Column(
            modifier = Modifier
                .background(articleItemState.contrastingColors.baseColor)
                .padding(0.dp)
                .clickable {
                    //ToDo, request animation navigation
                },
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(2f),
                contentAlignment = Alignment.BottomCenter
            ) {
                val averageColorTransparencyBrush =
                    Brush.verticalGradient(
                        listOf<Color>(
                            Color.Transparent,
                            articleItemState.contrastingColors.baseColor
                        )
                    )
                Image(
                    modifier = Modifier.fillMaxSize(),
                    bitmap = articleItemState.imageResult.toBitmap().asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f)
                        .background(averageColorTransparencyBrush)
                )
            }
            Text(
                text = articleItemState.articleDto.fields.headline ?: "TituloNoSalió",
                color = articleItemState.contrastingColors.contrastColor,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }
}

@Composable
fun ErrorCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
    ) {
        Text(text = "Todo mal")
    }
}

@Composable
fun EmptyLoadingCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(2f)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}
