package com.sercan.bookpedia.book.presentation.book_detail

import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.core.presentation.base.UiState

data class BookDetailState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val book: Book? = null,
    val isFavorite: Boolean = false
) : UiState {
    override fun copy(isLoading: Boolean, errorMessage: String?): UiState = BookDetailState(
        isLoading = isLoading,
        errorMessage = errorMessage,
        book = book,
        isFavorite = isFavorite
    )
}
