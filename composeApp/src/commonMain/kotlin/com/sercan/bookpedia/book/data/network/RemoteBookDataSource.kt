package com.sercan.bookpedia.book.data.network

import com.sercan.bookpedia.book.data.dto.BookWorkDto
import com.sercan.bookpedia.book.data.dto.SearchResponseDto
import com.sercan.bookpedia.book.data.dto.TrendingBooksDto
import com.sercan.bookpedia.core.domain.DataError
import com.sercan.bookpedia.core.domain.Result

interface RemoteBookDataSource {
    suspend fun searchBooks(
        query: String,
        resultLimit: Int? = null
    ): Result<SearchResponseDto, DataError.Remote>

    suspend fun getBookDetails(bookWorkId: String): Result<BookWorkDto, DataError.Remote>
    
    suspend fun getTrendingBooks(): Result<TrendingBooksDto, DataError.Remote>
}