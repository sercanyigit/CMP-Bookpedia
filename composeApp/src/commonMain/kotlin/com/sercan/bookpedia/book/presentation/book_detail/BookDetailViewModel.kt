package com.sercan.bookpedia.book.presentation.book_detail

import androidx.lifecycle.viewModelScope
import com.sercan.bookpedia.book.domain.usecase.GetBookDescriptionUseCase
import com.sercan.bookpedia.book.domain.usecase.IsBookFavoriteUseCase
import com.sercan.bookpedia.book.domain.usecase.ToggleFavoriteUseCase
import com.sercan.bookpedia.book.presentation.book_detail.state.BookDetailState
import com.sercan.bookpedia.core.domain.Result
import com.sercan.bookpedia.core.presentation.base.BaseViewModel
import com.sercan.bookpedia.core.presentation.utils.toUiText
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class BookDetailViewModel(
    private val getBookDescriptionUseCase: GetBookDescriptionUseCase,
    private val isBookFavoriteUseCase: IsBookFavoriteUseCase,
    private val toggleFavoriteUseCase: ToggleFavoriteUseCase
) : BaseViewModel<BookDetailState, BookDetailAction>(BookDetailState()) {

    override fun onAction(action: BookDetailAction) {
        when(action) {
            is BookDetailAction.OnSelectedBookChange -> {
                setState { copy(
                    book = action.book,
                    isLoading = true
                ) }
                action.book.id.let { bookId ->
                    observeFavoriteStatus(bookId)
                    fetchBookDescription(bookId)
                }
            }
            is BookDetailAction.OnFavoriteClick -> {
                viewModelScope.launch {
                    state.value.book?.let { book ->
                        toggleFavoriteUseCase(book)
                    }
                }
            }
            else -> Unit
        }
    }

    private fun observeFavoriteStatus(bookId: String) {
        isBookFavoriteUseCase(bookId)
            .onEach { isFavorite ->
                setState { copy(
                    isFavorite = isFavorite
                ) }
            }
            .launchIn(viewModelScope)
    }

    private fun fetchBookDescription(bookId: String) {
        viewModelScope.launch {
            showLoading()
            when (val result = getBookDescriptionUseCase(bookId)) {
                is Result.Success -> setState {
                    copy(
                        book = book?.copy(
                            description = result.data
                        ),
                        isLoading = false,
                        errorMessage = null
                    )
                }
                is Result.Error -> setState {
                    copy(
                        book = book,
                        isLoading = false,
                        errorMessage = result.error.toUiText().toString()
                    )
                }
            }
        }
    }
}