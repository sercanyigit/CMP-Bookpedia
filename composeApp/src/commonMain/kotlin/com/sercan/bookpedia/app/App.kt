package com.sercan.bookpedia.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.sercan.bookpedia.book.data.repository.GenreRepository
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.presentation.book_detail.BookDetailScreenRoot
import com.sercan.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.sercan.bookpedia.book.presentation.favorites.FavoritesScreen
import com.sercan.bookpedia.book.presentation.onboarding.OnboardingScreen
import com.sercan.bookpedia.book.presentation.onboarding.OnboardingViewModel
import com.sercan.bookpedia.book.presentation.search.SearchScreenRoot
import com.sercan.bookpedia.core.navigation.Route
import com.sercan.bookpedia.core.presentation.components.BottomBar
import org.koin.compose.koinInject

@Composable
fun App() {
    val genreRepository = koinInject<GenreRepository>()
    val onboardingViewModel = koinInject<OnboardingViewModel>()
    val selectedGenres by genreRepository.getSelectedGenres().collectAsState(initial = emptySet())
    var showOnboarding by remember { mutableStateOf(selectedGenres.isEmpty()) }

    MaterialTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (showOnboarding) {
                OnboardingScreen(
                    onFinish = { genres ->
                        onboardingViewModel.saveSelectedGenres(genres)
                        showOnboarding = false
                    }
                )
            } else {
                var currentRoute: Route by remember { mutableStateOf(Route.Home) }
                var selectedBook by remember { mutableStateOf<Book?>(null) }

                Scaffold(
                    bottomBar = {
                        if (selectedBook == null) {
                            BottomBar(
                                currentRoute = currentRoute,
                                onNavigate = { route ->
                                    currentRoute = route
                                }
                            )
                        }
                    }
                ) { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        selectedBook?.let { book ->
                            BookDetailScreenRoot(
                                onBackClick = {
                                    selectedBook = null
                                    currentRoute = Route.Home
                                },
                                selectedBook = book
                            )
                        } ?: when (currentRoute) {
                            is Route.Home -> {
                                BookListScreenRoot(
                                    onBookClick = { book ->
                                        selectedBook = book
                                        currentRoute = Route.BookDetails(book.id)
                                    }
                                )
                            }

                            is Route.Search -> {
                                SearchScreenRoot(
                                    onBookClick = { book ->
                                        selectedBook = book
                                        currentRoute = Route.BookDetails(book.id)
                                    }
                                )
                            }

                            is Route.Favorites -> {
                                FavoritesScreen(
                                    onBookClick = { book ->
                                        selectedBook = book
                                        currentRoute = Route.BookDetails(book.id)
                                    }
                                )
                            }

                            is Route.BookDetails -> {
                                // Bu durumda zaten selectedBook null değilse yukarıdaki let bloğunda işlenecek
                            }
                        }
                    }
                }
            }
        }
    }
}