package com.sercan.bookpedia.book.domain.usecase

import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.book.domain.model.Book
import kotlinx.coroutines.flow.Flow

class GetFavoriteBooksUseCase(
    private val repository: BookRepository
) {
    operator fun invoke(): Flow<List<Book>> {
        return repository.getFavoriteBooks()
    }
} 