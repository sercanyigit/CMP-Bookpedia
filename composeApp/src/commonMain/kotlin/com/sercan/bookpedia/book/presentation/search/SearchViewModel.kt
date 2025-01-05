package com.sercan.bookpedia.book.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.core.domain.Result
import com.sercan.bookpedia.core.presentation.toUiText
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val bookRepository: BookRepository
) : ViewModel() {

    private var searchJob: Job? = null

    private val _state = MutableStateFlow(SearchState())
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
        SearchState()
    )

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        _state
            .map { it.searchQuery }
            .distinctUntilChanged()
            .debounce(500L)
            .onEach { query ->
                when {
                    query.isBlank() -> {
                        _state.update {
                            it.copy(
                                errorMessage = null,
                                searchResults = emptyList()
                            )
                        }
                    }
                    query.length >= 2 -> {
                        searchJob?.cancel()
                        searchJob = searchBooks(query)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    private fun searchBooks(query: String) = viewModelScope.launch {
        _state.update {
            it.copy(
                isLoading = true
            )
        }
        when (val result = bookRepository.searchBooks(query)) {
            is Result.Success -> {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = null,
                        searchResults = result.data
                    )
                }
            }
            is Result.Error -> {
                _state.update {
                    it.copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = result.error.toUiText()
                    )
                }
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _state.update {
            it.copy(searchQuery = query)
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