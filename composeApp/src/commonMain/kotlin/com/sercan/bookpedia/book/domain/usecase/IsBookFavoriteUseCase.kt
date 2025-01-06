package com.sercan.bookpedia.book.domain.usecase

import com.sercan.bookpedia.book.domain.BookRepository
import kotlinx.coroutines.flow.Flow

class IsBookFavoriteUseCase(
    private val repository: BookRepository
) {
    operator fun invoke(bookId: String): Flow<Boolean> {
        return repository.isBookFavorite(bookId)
    }
} 