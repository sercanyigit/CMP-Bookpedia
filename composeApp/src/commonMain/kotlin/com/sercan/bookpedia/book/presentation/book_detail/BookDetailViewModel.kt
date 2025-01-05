package com.sercan.bookpedia.book.presentation.book_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.core.domain.onSuccess
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val bookRepository: BookRepository
): ViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        _state.value
    )

    fun onAction(action: BookDetailAction) {
        when(action) {
            is BookDetailAction.OnSelectedBookChange -> {
                _state.update { it.copy(
                    book = action.book,
                    isLoading = true
                ) }
                action.book.id.let { bookId ->
                    observeFavoriteStatus(bookId)
                    fetchBookDescription(bookId)
                }
            }
            is BookDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    state.value.book?.let { book ->
                        if(state.value.isFavorite) {
                            bookRepository.deleteFromFavorites(book.id)
                        } else {
                            bookRepository.markAsFavorite(book)
                        }
                    }
                }
            }
            else -> Unit
        }
    }

    private fun observeFavoriteStatus(bookId: String) {
        bookRepository
            .isBookFavorite(bookId)
            .onEach { isFavorite ->
                _state.update { it.copy(
                    isFavorite = isFavorite
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchBookDescription(bookId: String) {
        viewModelScope.launch {
            bookRepository
                .getBookDescription(bookId)
                .onSuccess { description ->
                    _state.update { it.copy(
                        book = it.book?.copy(
                            description = description
                        ),
                        isLoading = false
                    ) }
                }
        }
    }
}