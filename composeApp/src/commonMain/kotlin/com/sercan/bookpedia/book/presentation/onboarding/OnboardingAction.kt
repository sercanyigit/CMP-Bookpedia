package com.sercan.bookpedia.book.presentation.onboarding

sealed interface OnboardingAction {
    object NextPage : OnboardingAction
    object PreviousPage : OnboardingAction
    object Finish : OnboardingAction
    data class OnPageChanged(val page: Int) : OnboardingAction
} 