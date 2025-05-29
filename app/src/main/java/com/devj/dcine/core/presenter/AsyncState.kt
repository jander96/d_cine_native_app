package com.devj.dcine.core.presenter

enum class AsyncState {
    INITIAL,
    LOADING,
    SUCCESS,
    ERROR;

    val isLoading: Boolean get() = this == LOADING
    val isSuccess: Boolean get() = this == SUCCESS
    val isError: Boolean get() = this == ERROR
    val isInitial: Boolean get() = this == INITIAL
}