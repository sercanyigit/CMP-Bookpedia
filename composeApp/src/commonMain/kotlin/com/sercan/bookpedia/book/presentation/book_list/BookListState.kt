package com.sercan.bookpedia.book.presentation.book_list

import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.core.presentation.base.UiState

data class BookListState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val searchResults: List<Book> = emptyList(),
    val favoriteBooks: List<Book> = emptyList(),
    val searchQuery: String = "",
    val selectedTabIndex: Int = 0
) : UiState {
    override fun copy(isLoading: Boolean, errorMessage: String?): UiState = BookListState(
        isLoading = isLoading,
        errorMessage = errorMessage,
        searchResults = searchResults,
        favoriteBooks = favoriteBooks,
        searchQuery = searchQuery,
        selectedTabIndex = selectedTabIndex
    )
}