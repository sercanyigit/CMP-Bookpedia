@file:OptIn(FlowPreview::class)

package com.sercan.bookpedia.book.presentation.book_list

import androidx.lifecycle.viewModelScope
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.core.domain.Result
import com.sercan.bookpedia.core.presentation.base.BaseViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

class BookListViewModel(
    private val bookRepository: BookRepository
) : BaseViewModel<BookListState, BookListAction>(BookListState()) {

    init {
        loadTrendingBooks()
    }

    private fun loadTrendingBooks() {
        viewModelScope.launch {
            showLoading()
            when (val result = bookRepository.getTrendingBooks()) {
                is Result.Success -> setState {
                    copy(
                        searchResults = result.data,
                        isLoading = false,
                        errorMessage = null
                    )
                }
                is Result.Error -> setState { 
                    copy(
                        searchResults = emptyList(),
                        isLoading = false,
                        errorMessage = result.error.toString()
                    ) as BookListState
                }
            }
        }
    }

    override fun onAction(action: BookListAction) {
        when (action) {
            is BookListAction.OnBookClick -> Unit
            is BookListAction.OnSearchQueryChange -> setState { 
                copy(searchQuery = action.query) 
            }
            is BookListAction.OnTabSelected -> setState { 
                copy(selectedTabIndex = action.index) 
            }
            is BookListAction.OnFavoriteClick -> toggleFavorite(action.book)
        }
    }

    private fun toggleFavorite(book: Book) {
        viewModelScope.launch {
            val isFavorite = state.value.favoriteBooks.any { it.id == book.id }
            if (isFavorite) {
                bookRepository.deleteFromFavorites(book.id)
            } else {
                bookRepository.markAsFavorite(book)
            }
        }
    }
}