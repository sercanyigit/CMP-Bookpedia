package com.sercan.bookpedia.book.presentation.favorites

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.no_favorite_books
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.presentation.book_list.BookListAction
import com.sercan.bookpedia.book.presentation.book_list.BookListState
import com.sercan.bookpedia.book.presentation.book_list.BookListViewModel
import com.sercan.bookpedia.book.presentation.book_list.components.BookList
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun FavoritesScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    FavoritesScreen(
        state = state,
        onAction = { action ->
            when(action) {
                is BookListAction.OnBookClick -> onBookClick(action.book)
                else -> Unit
            }
            viewModel.onAction(action)
        }
    )
}

@Composable
fun FavoritesScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Favorites",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        if(state.favoriteBooks.isEmpty()) {
            Text(
                text = stringResource(Res.string.no_favorite_books),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.headlineSmall,
            )
        } else {
            BookList(
                books = state.favoriteBooks,
                onBookClick = {
                    onAction(BookListAction.OnBookClick(it))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
} 