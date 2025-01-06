package com.sercan.bookpedia.book.domain.usecase

import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.book.domain.model.Book
import com.sercan.bookpedia.core.domain.DataError
import com.sercan.bookpedia.core.domain.Result

class GetTrendingBooksUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(): Result<List<Book>, DataError.Remote> {
        return repository.getTrendingBooks()
    }
} 