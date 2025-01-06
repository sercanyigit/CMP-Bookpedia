package com.sercan.bookpedia.book.presentation.favorites

import com.sercan.bookpedia.book.domain.model.Book

sealed interface FavoritesAction {
    data class OnBookClick(val book: Book) : FavoritesAction
    data class OnRemoveClick(val book: Book) : FavoritesAction
} 