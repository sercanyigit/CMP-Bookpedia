package com.sercan.bookpedia.book.domain

import com.sercan.bookpedia.core.domain.DataError
import com.sercan.bookpedia.core.domain.EmptyResult
import com.sercan.bookpedia.core.domain.Result
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    suspend fun searchBooks(query: String): Result<List<Book>, DataError.Remote>
    suspend fun getBookDescription(bookId: String): Result<String?, DataError>
    fun getFavoriteBooks(): Flow<List<Book>>
    fun isBookFavorite(id: String): Flow<Boolean>
    suspend fun markAsFavorite(book: Book): EmptyResult<DataError.Local>
    suspend fun deleteFromFavorites(id: String)
    suspend fun getTrendingBooks(): Result<List<Book>, DataError.Remote>
}