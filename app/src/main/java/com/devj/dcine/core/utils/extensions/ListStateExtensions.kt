package com.devj.dcine.core.utils.extensions

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.carousel.CarouselState

internal fun LazyListState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return  (lastVisibleItem?.index ?: 0) >= (layoutInfo.totalItemsCount -1) -buffer
}

internal fun LazyGridState.reachedBottom(buffer: Int = 1): Boolean {
    val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
    return  (lastVisibleItem?.index ?: 0) >= (layoutInfo.totalItemsCount -1) -buffer
}
@OptIn(ExperimentalMaterial3Api::class)
internal fun CarouselState.reachedBottom(buffer: Int = 1): Boolean {
    return !canScrollForward
}
