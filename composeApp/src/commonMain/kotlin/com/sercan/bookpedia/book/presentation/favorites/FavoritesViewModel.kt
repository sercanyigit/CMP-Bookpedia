package com.sercan.bookpedia.book.presentation.favorites

import androidx.lifecycle.viewModelScope
import com.sercan.bookpedia.book.domain.model.Book
import com.sercan.bookpedia.book.domain.usecase.GetFavoriteBooksUseCase
import com.sercan.bookpedia.book.domain.usecase.ToggleFavoriteUseCase
import com.sercan.bookpedia.book.presentation.favorites.state.FavoritesState
import com.sercan.bookpedia.core.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavoritesViewModel(
    private val getFavoriteBooksUseCase: GetFavoriteBooksUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel<FavoritesState, FavoritesAction>(FavoritesState()) {

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            getFavoriteBooksUseCase()
                .collectLatest { books ->
                    setState {
                        copy(books = books)
                    }
                }
        }
    }

    override fun onAction(action: FavoritesAction) {
        when(action) {
            is FavoritesAction.OnBookClick -> Unit // Handle in UI
            is FavoritesAction.OnRemoveClick -> removeFromFavorites(action.book)
        }
    }

    private fun removeFromFavorites(book: Book) {
        viewModelScope.launch {
            toggleFavoriteUseCase(book)
        }
    }
} 