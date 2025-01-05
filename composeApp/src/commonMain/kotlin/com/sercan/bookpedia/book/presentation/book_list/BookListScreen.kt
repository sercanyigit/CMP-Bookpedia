package com.sercan.bookpedia.book.presentation.book_list

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.favorites
import cmp_bookpedia.composeapp.generated.resources.no_favorite_books
import cmp_bookpedia.composeapp.generated.resources.no_search_results
import cmp_bookpedia.composeapp.generated.resources.search_results
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.presentation.book_list.components.BookList
import com.sercan.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.sercan.bookpedia.core.presentation.DarkBlue
import com.sercan.bookpedia.core.presentation.DesertWhite
import com.sercan.bookpedia.core.presentation.SandYellow
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    BookListScreen(
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
fun BookListScreen(
    state: BookListState,
    onAction: (BookListAction) -> Unit,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val searchResultsListState = rememberLazyListState()
    val favoriteBooksListState = rememberLazyListState()

    LaunchedEffect(state.searchResults) {
        searchResultsListState.animateScrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .statusBarsPadding(),
    ) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(16.dp)
        )
        
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Recommendation",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.searchResults.take(5)) { book ->
                    BookCoverItem(
                        book = book,
                        onClick = { onAction(BookListAction.OnBookClick(book)) }
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Just for you",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
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
                            modifier = Modifier.fillMaxWidth(),
                            scrollState = searchResultsListState
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun BookCoverItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .width(120.dp)
            .height(180.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 4.dp
    ) {
        AsyncImage(
            model = book.imageUrl,
            contentDescription = book.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}