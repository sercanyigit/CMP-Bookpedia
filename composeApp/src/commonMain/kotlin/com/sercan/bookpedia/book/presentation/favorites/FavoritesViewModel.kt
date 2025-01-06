package com.sercan.bookpedia.book.presentation.favorites

import androidx.lifecycle.viewModelScope
import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val repository: BookRepository
) : BaseViewModel<FavoritesState, FavoritesAction>(FavoritesState()) {

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            repository.getFavoriteBooks().collectLatest { books ->
                setState {
                    copy(books = books)
                }
            }
        }
    }

    override fun onAction(action: FavoritesAction) {
        when(action) {
            is FavoritesAction.OnBookClick -> Unit // Handle in UI
            is FavoritesAction.OnRemoveClick -> removeFromFavorites(action.book.id)
        }
    }

    private fun removeFromFavorites(bookId: String) {
        viewModelScope.launch {
            repository.deleteFromFavorites(bookId)
        }
    }
} 