package com.sercan.bookpedia.book.presentation.search

import com.sercan.bookpedia.book.domain.model.Book

sealed interface SearchAction {
    data class OnQueryChange(val query: String) : SearchAction
    data class OnBookClick(val book: Book) : SearchAction
    data class OnFavoriteClick(val book: Book) : SearchAction
    object OnRetry : SearchAction
} 