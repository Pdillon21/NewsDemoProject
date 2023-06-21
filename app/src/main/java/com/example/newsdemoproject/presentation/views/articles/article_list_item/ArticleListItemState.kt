package com.example.newsdemoproject.presentation.views.articles.article_list_item

import android.graphics.drawable.Drawable
import com.example.newsdemoproject.data.remote.dto.single_item.GuardianSingleItemDto
import com.example.newsdemoproject.presentation.utils.ContrastingColors

sealed class ArticleListItemState {
    object Loading : ArticleListItemState()
    data class Success(
        val contrastingColors: ContrastingColors,
        val articleDto: GuardianSingleItemDto,
        val imageResult: Drawable
    ) : ArticleListItemState()
    object Error : ArticleListItemState()
}
