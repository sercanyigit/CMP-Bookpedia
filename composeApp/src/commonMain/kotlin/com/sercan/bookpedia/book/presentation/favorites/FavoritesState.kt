package com.sercan.bookpedia.book.presentation.favorites

import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.core.presentation.base.UiState

data class FavoritesState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val books: List<Book> = emptyList()
) : UiState {
    override fun copy(isLoading: Boolean, errorMessage: String?): UiState = FavoritesState(
        isLoading = isLoading,
        errorMessage = errorMessage,
        books = books
    )
} 