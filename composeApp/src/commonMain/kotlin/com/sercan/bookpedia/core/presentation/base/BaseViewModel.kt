package com.sercan.bookpedia.core.presentation.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<State : UiState, Action>(initialState: State) : ViewModel() {
    private val _state = MutableStateFlow(initialState)
    val state = _state.asStateFlow()
    
    protected val _isDarkMode = MutableStateFlow(false)
    val isDarkMode: StateFlow<Boolean> = _isDarkMode.asStateFlow()
    
    abstract fun onAction(action: Action)
    
    protected fun setState(reducer: State.() -> State) {
        val currentState = _state.value
        _state.value = currentState.reducer()
    }

    protected fun toggleTheme() {
        _isDarkMode.value = !_isDarkMode.value
    }

    protected fun showLoading() {
        setState { 
            @Suppress("UNCHECKED_CAST")
            copy(isLoading = true) as State
        }
    }

    protected fun hideLoading() {
        setState { 
            @Suppress("UNCHECKED_CAST")
            copy(isLoading = false) as State
        }
    }

    protected fun showError(message: String) {
        setState { 
            @Suppress("UNCHECKED_CAST")
            copy(isLoading = false, errorMessage = message) as State
        }
    }
}