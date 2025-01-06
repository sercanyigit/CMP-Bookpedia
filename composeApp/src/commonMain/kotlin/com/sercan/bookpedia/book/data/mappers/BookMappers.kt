package com.sercan.bookpedia.book.data.mappers

import com.sercan.bookpedia.book.data.database.BookEntity
import com.sercan.bookpedia.book.data.dto.SearchedBookDto
import com.sercan.bookpedia.book.data.dto.TrendingBookDto
import com.sercan.bookpedia.book.domain.model.Book

fun SearchedBookDto.toBook(): Book {
    return Book(
        id = id.substringAfterLast("/"),
        title = title,
        imageUrl = if(coverKey != null) {
            "https://covers.openlibrary.org/b/olid/${coverKey}-L.jpg"
        } else {
            "https://covers.openlibrary.org/b/id/${coverAlternativeKey}-L.jpg"
        },
        authors = authorNames ?: emptyList(),
        description = null,
        languages = languages ?: emptyList(),
        firstPublishYear = firstPublishYear.toString(),
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions ?: 0
    )
}

fun Book.toBookEntity(): BookEntity {
    return BookEntity(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        ratingsAverage = averageRating,
        ratingsCount = ratingCount,
        numPagesMedian = numPages,
        numEditions = numEditions
    )
}

fun BookEntity.toBook(): Book {
    return Book(
        id = id,
        title = title,
        description = description,
        imageUrl = imageUrl,
        languages = languages,
        authors = authors,
        firstPublishYear = firstPublishYear,
        averageRating = ratingsAverage,
        ratingCount = ratingsCount,
        numPages = numPagesMedian,
        numEditions = numEditions
    )
}

fun TrendingBookDto.toBook(): Book {
    val coverUrl = if (coverId != null) {
        "https://covers.openlibrary.org/b/id/$coverId-L.jpg"
    } else {
        "https://openlibrary.org/images/icons/avatar_book.png"
    }
    
    return Book(
        id = key.removePrefix("/works/"),
        title = title,
        imageUrl = coverUrl,
        authors = authorNames ?: listOf("Unknown Author"),
        description = null,
        languages = emptyList(),
        firstPublishYear = firstPublishYear?.toString(),
        averageRating = null,
        ratingCount = null,
        numPages = null,
        numEditions = editionCount ?: 0
    )
}