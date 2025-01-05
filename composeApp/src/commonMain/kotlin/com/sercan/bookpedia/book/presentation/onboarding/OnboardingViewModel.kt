package com.sercan.bookpedia.book.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sercan.bookpedia.book.data.repository.GenreRepository
import com.sercan.bookpedia.book.domain.Genre
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val genreRepository: GenreRepository
) : ViewModel() {

    fun saveSelectedGenres(genres: Set<Genre>) {
        viewModelScope.launch {
            genreRepository.saveSelectedGenres(genres)
        }
    }
} 