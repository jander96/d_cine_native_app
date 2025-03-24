package com.devj.dcine.core.utils.extensions

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState

internal fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return  lastVisibleItem?.index == (layoutInfo.totalItemsCount -1) -buffer
}

internal fun LazyGridState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return  (lastVisibleItem?.index ?: 0) >= (layoutInfo.totalItemsCount -1) -buffer
}