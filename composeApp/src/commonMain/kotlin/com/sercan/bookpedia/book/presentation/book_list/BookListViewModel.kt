@file:OptIn(FlowPreview::class)

package com.sercan.bookpedia.book.presentation.book_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.core.domain.Result
import com.sercan.bookpedia.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private var cachedBooks = emptyList<Book>()

    private val _state = MutableStateFlow(BookListState())
    val state = combine(
        _state,
        bookRepository.getFavoriteBooks()
    ) { state, favoriteBooks ->
        state.copy(
            favoriteBooks = favoriteBooks,
            isLoading = state.isLoading
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        BookListState()
    )

    init {
        loadTrendingBooks()
    }

    private fun loadTrendingBooks() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            
            when (val result = bookRepository.getTrendingBooks()) {
                is Result.Success -> {
                    cachedBooks = result.data
                    _state.update {
                        it.copy(
                            searchResults = result.data,
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                }
                is Result.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = result.error.toUiText()
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> {
                // Handle book click
            }
            is BookListAction.OnSearchQueryChange -> {
                _state.update {
                    it.copy(searchQuery = action.query)
                }
            }
            is BookListAction.OnTabSelected -> {
                _state.update {
                    it.copy(selectedTabIndex = action.index)
                }
            }
        }
    }

    fun removeFromFavorites(book: Book) {
        viewModelScope.launch {
            bookRepository.deleteFromFavorites(book.id)
        }
    }

    fun toggleFavorite(book: Book) {
        viewModelScope.launch {
            val isFavorite = state.value.favoriteBooks.any { it.id == book.id }
            if (isFavorite) {
                bookRepository.deleteFromFavorites(book.id)
            } else {
                bookRepository.markAsFavorite(book)
            }
        }
    }
}