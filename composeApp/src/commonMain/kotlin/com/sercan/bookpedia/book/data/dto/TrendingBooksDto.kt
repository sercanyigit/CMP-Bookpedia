package com.sercan.bookpedia.book.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrendingBooksDto(
    val works: List<TrendingBookDto>,
    val query: String? = null,
    val status: String? = null
)

@Serializable
data class TrendingBookDto(
    val key: String,
    val title: String,
    @SerialName("author_name")
    val authorNames: List<String>? = null,
    @SerialName("cover_i")
    val coverId: Int? = null,
    @SerialName("edition_count")
    val editionCount: Int? = null,
    @SerialName("first_publish_year")
    val firstPublishYear: Int? = null
) 