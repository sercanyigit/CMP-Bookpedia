package com.sercan.bookpedia.book.presentation.search

import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.core.presentation.UiText

data class SearchState(
    val searchQuery: String = "",
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: UiText? = null
) 