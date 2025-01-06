package com.sercan.bookpedia.core.presentation.base

import androidx.lifecycle.ViewModel
import com.sercan.bookpedia.book.presentation.book_list.BookListState
import com.sercan.bookpedia.book.presentation.favorites.FavoritesState
import com.sercan.bookpedia.book.presentation.onboarding.OnboardingState
import com.sercan.bookpedia.book.presentation.search.SearchState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State : UiState, Action>(initialState: State) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    
    abstract fun onAction(action: Action)
    
    protected fun setState(reducer: State.() -> State) {
        val currentState = _state.value
        _state.value = currentState.reducer()
    }

    protected fun showLoading() {
        setState { 
            when (this) {
                is BookListState -> copy(isLoading = true)
                is SearchState -> copy(isLoading = true)
                is FavoritesState -> copy(isLoading = true)
                is OnboardingState -> copy(isLoading = true)
                else -> this
            } as State
        }
    }

    protected fun hideLoading() {
        setState { 
            when (this) {
                is BookListState -> copy(isLoading = false)
                is SearchState -> copy(isLoading = false)
                is FavoritesState -> copy(isLoading = false)
                is OnboardingState -> copy(isLoading = false)
                else -> this
            } as State
        }
    }

    protected fun showError(message: String) {
        setState { 
            when (this) {
                is BookListState -> copy(isLoading = false, errorMessage = message)
                is SearchState -> copy(isLoading = false, errorMessage = message)
                is FavoritesState -> copy(isLoading = false, errorMessage = message)
                is OnboardingState -> copy(isLoading = false, errorMessage = message)
                else -> this
            } as State
        }
    }
} 