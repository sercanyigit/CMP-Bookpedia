package com.sercan.bookpedia.book.data.repository

import com.sercan.bookpedia.book.domain.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GenreRepository {
    private val selectedGenres = MutableStateFlow<Set<Genre>>(emptySet())

    fun getSelectedGenres(): Flow<Set<Genre>> = selectedGenres

    suspend fun saveSelectedGenres(genres: Set<Genre>) {
        selectedGenres.value = genres
    }
} 