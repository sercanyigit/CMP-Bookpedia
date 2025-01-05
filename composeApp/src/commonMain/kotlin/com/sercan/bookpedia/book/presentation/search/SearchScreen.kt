package com.sercan.bookpedia.book.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.no_search_results
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.presentation.book_list.BookListAction
import com.sercan.bookpedia.book.presentation.book_list.BookListState
import com.sercan.bookpedia.book.presentation.book_list.BookListViewModel
import com.sercan.bookpedia.book.presentation.book_list.components.BookList
import com.sercan.bookpedia.book.presentation.book_list.components.BookSearchBar
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
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
fun SearchScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = {
                onAction(BookListAction.OnSearchQueryChange(it))
            },
            onImeSearch = {},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        if(state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            when {
                state.errorMessage != null -> {
                    Text(
                        text = state.errorMessage.asString(),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                state.searchResults.isEmpty() -> {
                    Text(
                        text = stringResource(Res.string.no_search_results),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                else -> {
                    BookList(
                        books = state.searchResults,
                        onBookClick = {
                            onAction(BookListAction.OnBookClick(it))
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
} 