package com.sercan.bookpedia.book.presentation.onboarding

import com.sercan.bookpedia.core.presentation.base.BaseViewModel

class OnboardingViewModel : BaseViewModel<OnboardingState, OnboardingAction>(OnboardingState()) {
    
    companion object {
        val onboardingPages = listOf(
            com.sercan.bookpedia.book.domain.model.OnboardingPage(
                title = "Kitap Dünyasına Hoş Geldiniz",
                description = "Binlerce kitap arasında keşfe çıkın, yeni hikayeler ve bilgiler edinin",
                lottieRes = "onboarding1.json"
            ),
            com.sercan.bookpedia.book.domain.model.OnboardingPage(
                title = "Favori Kitaplarınızı Kaydedin",
                description = "Beğendiğiniz kitapları favorilerinize ekleyerek daha sonra kolayca ulaşın",
                lottieRes = "onboarding2.json"
            ),
            com.sercan.bookpedia.book.domain.model.OnboardingPage(
                title = "Hemen Keşfetmeye Başlayın",
                description = "Aradığınız tüm kitaplara kolayca ulaşabilir, detaylı bilgilere göz atabilirsiniz",
                lottieRes = "search.json"
            )
        )
    }
    
    override fun onAction(action: OnboardingAction) {
        when(action) {
            OnboardingAction.NextPage -> setState { 
                copy(
                    currentPage = currentPage + 1,
                    isLastPage = currentPage + 1 == onboardingPages.lastIndex
                )
            }
            OnboardingAction.PreviousPage -> setState { 
                copy(
                    currentPage = currentPage - 1,
                    isLastPage = false
                )
            }
            OnboardingAction.Finish -> {
                // Handle finish action
            }
            is OnboardingAction.OnPageChanged -> setState {
                copy(
                    currentPage = action.page,
                    isLastPage = action.page == onboardingPages.lastIndex
                )
            }
        }
    }
} 