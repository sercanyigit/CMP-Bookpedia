package com.sercan.bookpedia.book.presentation.onboarding

import com.sercan.bookpedia.core.presentation.base.UiState

data class OnboardingState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val currentPage: Int = 0,
    val isLastPage: Boolean = false
) : UiState {
    override fun copy(isLoading: Boolean, errorMessage: String?): UiState = OnboardingState(
        isLoading = isLoading,
        errorMessage = errorMessage,
        currentPage = currentPage,
        isLastPage = isLastPage
    )
} 