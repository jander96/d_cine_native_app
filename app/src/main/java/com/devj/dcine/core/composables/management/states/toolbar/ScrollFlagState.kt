package com.devj.dcine.core.composables.management.states.toolbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.structuralEqualityPolicy

abstract class ScrollFlagState(heightRange: IntRange, scrollValue: Int) : ToolbarState {
    init {
        require(heightRange.first >= 0 && heightRange.last >= heightRange.first) {
            "The lowest height value must be >= 0 and the highest height value must be >= the lowest value."
        }
    }
    protected  val minHeight = heightRange.first
    protected  val maxHeight = heightRange.last
    protected  val rangeDifference = heightRange.last - heightRange.first

    protected var scrollValueState by mutableIntStateOf(scrollValue.coerceAtLeast(0))

    final override val progress: Float
        get() = 1f - (maxHeight - height) / rangeDifference
}