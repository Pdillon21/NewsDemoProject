package com.example.newsdemoproject.presentation.views.articles

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.newsdemoproject.data.remote.dto.single_item.GuardianSingleItemDto
import com.example.newsdemoproject.presentation.EventRequest
import com.example.newsdemoproject.presentation.views.articles.article_list_item.ColorTintedArticleListItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsList(
    modifier: Modifier = Modifier,
    list: List<GuardianSingleItemDto>,
    lazyEnabled: Boolean,
    enableSwipeToRefresh: Boolean = false,
    eventRequest: (EventRequest) -> Unit
) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = false,
        { eventRequest(EventRequest.RefreshEvent) }
    )
    if (enableSwipeToRefresh) {
        NewsListSwipe(
            modifier = modifier,
            list = list,
            lazyEnabled = lazyEnabled,
            pullRefreshState = pullRefreshState
        ) { event ->
            eventRequest(event)
        }
    } else {
        NewsListLazyColumn(
            modifier = modifier,
            lazyEnabled = lazyEnabled,
            list = list
        ) { event ->
            eventRequest(event)
        }
    }
}

@Composable
fun NewsListLazyColumn(
    modifier: Modifier,
    lazyEnabled: Boolean,
    list: List<GuardianSingleItemDto>,
    eventRequest: (EventRequest) -> Unit
) {
    if (lazyEnabled) {
        LazyArticleList(
            modifier = modifier,
            list = list
        ) {
            eventRequest(it)
        }
    } else {
        NonLazyArticleList(
            modifier = modifier,
            list = list
        ) {
            eventRequest(it)
        }
    }
}

@Composable
fun NonLazyArticleList(
    modifier: Modifier,
    list: List<GuardianSingleItemDto>,
    eventRequest: (EventRequest) -> Unit
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        for (article in list) {
            ColorTintedArticleListItem(
                modifier = Modifier.fillMaxWidth(),
                item = article
            )
        }
    }
}

@Composable
fun LazyArticleList(
    modifier: Modifier,
    list: List<GuardianSingleItemDto>,
    eventRequest: (EventRequest) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(
            vertical = 8.dp,
            horizontal = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // val randomIndex = Random.nextInt(from = 0, until = list.size - 2)
        items(
            items = list, // .subList(randomIndex, randomIndex + 1),
            itemContent = { singleItem ->
                ColorTintedArticleListItem(modifier = Modifier.fillMaxWidth(), item = singleItem)
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewsListSwipe(
    modifier: Modifier = Modifier,
    list: List<GuardianSingleItemDto>,
    lazyEnabled: Boolean,
    pullRefreshState: PullRefreshState,
    eventRequest: (EventRequest) -> Unit
) {
    Box(
        modifier = modifier
            .pullRefresh(pullRefreshState)
    ) {
        if (lazyEnabled) {
            LazyArticleList(
                modifier = modifier,
                list = list,
                eventRequest = { eventRequest(it) })
        } else {
            NonLazyArticleList(
                modifier = modifier,
                list = list,
                eventRequest = { eventRequest(it) })
        }
    }
}
