package com.sercan.bookpedia.book.presentation.search.state

import com.sercan.bookpedia.book.domain.model.Book
import com.sercan.bookpedia.core.presentation.base.UiState

data class SearchState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList()
) : UiState {
    override fun copy(isLoading: Boolean, errorMessage: String?): UiState = SearchState(
        isLoading = isLoading,
        errorMessage = errorMessage,
        searchQuery = searchQuery,
        searchResults = searchResults,
        favoriteBooks = favoriteBooks
    )
} 