package com.sercan.bookpedia.book.domain.usecase

import com.sercan.bookpedia.book.domain.BookRepository
import com.sercan.bookpedia.book.domain.model.Book
import kotlinx.coroutines.flow.first

class ToggleFavoriteUseCase(
    private val repository: BookRepository
) {
    suspend operator fun invoke(book: Book) {
        val isFavorite = repository.isBookFavorite(book.id).first()
        if (isFavorite) {
            repository.deleteFromFavorites(book.id)
        } else {
            repository.markAsFavorite(book)
        }
    }
} 