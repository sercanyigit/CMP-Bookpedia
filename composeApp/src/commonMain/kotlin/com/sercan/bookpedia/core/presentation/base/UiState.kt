package com.sercan.bookpedia.core.presentation.base

interface UiState {
    val isLoading: Boolean
    val errorMessage: String?

    fun copy(
        isLoading: Boolean = this.isLoading,
        errorMessage: String? = this.errorMessage
    ): UiState
}