package com.sercan.bookpedia.book.presentation.book_list

import com.sercan.bookpedia.book.domain.model.Book

sealed interface BookListAction {
    data class OnBookClick(val book: Book) : BookListAction
    data class OnSearchQueryChange(val query: String) : BookListAction
    data class OnTabSelected(val index: Int) : BookListAction
    data class OnFavoriteClick(val book: Book) : BookListAction
    data object LoadBooks : BookListAction
    data object OnThemeToggle : BookListAction
}