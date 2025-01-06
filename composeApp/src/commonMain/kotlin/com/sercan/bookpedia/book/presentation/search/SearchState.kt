package com.sercan.bookpedia.book.presentation.search

import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.core.presentation.base.UiState

data class SearchState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList()
) : UiState 