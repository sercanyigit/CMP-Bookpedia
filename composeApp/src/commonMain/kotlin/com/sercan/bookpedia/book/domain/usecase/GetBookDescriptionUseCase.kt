package com.sercan.bookpedia.book.domain.usecase

import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.core.domain.DataError
import com.sercan.bookpedia.core.domain.Result

class GetBookDescriptionUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(bookId: String): Result<String?, DataError> {
        return repository.getBookDescription(bookId)
    }
} 