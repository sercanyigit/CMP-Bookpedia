package com.sercan.bookpedia.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sercan.bookpedia.book.domain.model.Book
import com.sercan.bookpedia.book.presentation.book_detail.BookDetailScreenRoot
import com.sercan.bookpedia.book.presentation.book_list.BookListScreenRoot
import com.sercan.bookpedia.book.presentation.book_list.BookListViewModel
import com.sercan.bookpedia.book.presentation.favorites.FavoritesScreenRoot
import com.sercan.bookpedia.book.presentation.onboarding.OnboardingScreenRoot
import com.sercan.bookpedia.book.presentation.search.SearchScreenRoot
import com.sercan.bookpedia.core.navigation.Route
import com.sercan.bookpedia.core.presentation.components.BottomBar
import com.sercan.bookpedia.core.presentation.utils.DarkColors
import com.sercan.bookpedia.core.presentation.utils.LightColors
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    var showOnboarding by remember { mutableStateOf(true) }
    val viewModel: BookListViewModel = koinViewModel()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()

    MaterialTheme(
        colorScheme = if (isDarkMode) DarkColors else LightColors
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            if (showOnboarding) {
                OnboardingScreenRoot(
                    onFinish = {
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
                                FavoritesScreenRoot(
                                    onBookClick = { book ->
                                        selectedBook = book
                                        currentRoute = Route.BookDetails(book.id)
                                    }
                                )
                            }
                            else -> Unit
                        }
                    }
                }
            }
        }
    }
}