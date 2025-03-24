package com.devj.dcine.core.composables

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.devj.dcine.core.utils.extensions.reachedBottom
import kotlinx.coroutines.launch

@Composable
fun <T> InfiniteGrid(
    modifier: Modifier = Modifier,
    columns: GridCells,
    elements: List<T>,
    itemBuilder: @Composable (value: T) -> Unit,
    gridState: LazyGridState = rememberLazyGridState(),
    buffer: Int = 2,
    onLoadMore: suspend () -> Unit,
    paginationState: PaginationState,
    initialLoadingBuilder: @Composable () -> Unit = { CircularProgressIndicator() },
    errorBuilder: @Composable () -> Unit = { Text(text = "Error") },
    loadingNextBuilder: @Composable () -> Unit = { CircularProgressIndicator() },
    emptyBuilder: @Composable () -> Unit = {},
    endOfListBuilder: @Composable () -> Unit = {},
) {
    val scope = rememberCoroutineScope()

    val reachedBottom: Boolean by remember {
        derivedStateOf { gridState.reachedBottom(buffer = buffer) }
    }

    LaunchedEffect(reachedBottom) {
        if (reachedBottom) scope.launch {
            onLoadMore()
        }
    }
    if (elements.isEmpty() && paginationState.isIdle) {
        emptyBuilder()
    }
    if(paginationState.isInitialLoading){
        initialLoadingBuilder()
    }
    LazyVerticalGrid(
        columns = columns,
        state = gridState,
        modifier = modifier,
    ) {
        items(elements) {
            itemBuilder(it)
        }
        item {
            when (paginationState) {
                PaginationState.IDLE -> initialLoadingBuilder()
                PaginationState.LOADING -> {}
                PaginationState.PAGINATING -> loadingNextBuilder()
                PaginationState.ERROR -> errorBuilder()
                PaginationState.PAGINATION_EXHAUSTED -> endOfListBuilder()
            }
        }
    }


}

enum class PaginationState {
    IDLE, LOADING, PAGINATING, ERROR, PAGINATION_EXHAUSTED;

    val isInitialLoading: Boolean get() = this == LOADING
    val loadNextPage: Boolean get() = this == PAGINATING
    val isError: Boolean get() = this == ERROR
    val isIdle: Boolean get() = this == IDLE

}