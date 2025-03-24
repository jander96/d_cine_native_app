package com.devj.dcine.core.composables.management.states.toolbar

abstract class DynamicOffsetScrollFlagState(
    heightRange: IntRange,
    scrollValue: Int
) : ScrollFlagState(heightRange, scrollValue) {
    protected  abstract var scrollOffset: Float
}