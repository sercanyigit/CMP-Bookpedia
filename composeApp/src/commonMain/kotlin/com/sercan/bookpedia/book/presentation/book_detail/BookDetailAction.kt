package com.sercan.bookpedia.book.presentation.book_detail

import com.sercan.bookpedia.book.domain.model.Book

sealed interface BookDetailAction {
    data object OnBackClick: BookDetailAction
    data object OnFavoriteClick: BookDetailAction
    data class OnSelectedBookChange(val book: Book): BookDetailAction
}