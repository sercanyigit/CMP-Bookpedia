package com.sercan.bookpedia.book.presentation.search

import androidx.lifecycle.viewModelScope
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.core.domain.Result
import com.sercan.bookpedia.core.presentation.base.BaseViewModel
import com.sercan.bookpedia.core.presentation.utils.Constants
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@OptIn(FlowPreview::class)
class SearchViewModel(
    private val repository: BookRepository
) : BaseViewModel<SearchState, SearchAction>(SearchState()) {

    private var searchJob: Job? = null

    init {
        observeSearchQuery()
    }

    private fun observeSearchQuery() {
        viewModelScope.launch {
            state
                .map { it.searchQuery }
                .distinctUntilChanged()
                .debounce(Constants.Animation.SEARCH_DEBOUNCE)
                .collect { query ->
                    when {
                        query.isBlank() -> setState {
                            copy(
                                errorMessage = null,
                                searchResults = emptyList()
                            )
                        }
                        query.length >= Constants.Search.MIN_QUERY_LENGTH -> searchBooks(query)
                    }
                }
        }
    }

    private fun searchBooks(query: String) {
        viewModelScope.launch {
            showLoading()
            when (val result = repository.searchBooks(query)) {
                is Result.Success -> setState {
                    copy(
                        searchResults = result.data,
                        isLoading = false,
                        errorMessage = null
                    )
                }
                is Result.Error -> setState { 
                    copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = result.error.toString()
                    ) as SearchState
                }
            }
        }
    }

    override fun onAction(action: SearchAction) {
        when(action) {
            is SearchAction.OnQueryChange -> setState {
                copy(searchQuery = action.query)
            }
            is SearchAction.OnBookClick -> Unit
            is SearchAction.OnFavoriteClick -> toggleFavorite(action.book)
            SearchAction.OnRetry -> searchBooks(state.value.searchQuery)
        }
    }

    private fun toggleFavorite(book: Book) {
        viewModelScope.launch {
            val isFavorite = state.value.favoriteBooks.any { it.id == book.id }
            if (isFavorite) {
                repository.deleteFromFavorites(book.id)
            } else {
                repository.markAsFavorite(book)
            }
        }
    }
} 