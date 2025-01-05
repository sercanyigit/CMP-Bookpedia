package com.sercan.bookpedia.book.presentation.book_detail

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sercan.bookpedia.book.domain.Book
import org.koin.compose.koinInject

@Composable
fun BookDetailScreenRoot(
    onBackClick: () -> Unit,
    selectedBook: Book
) {
    val viewModel = koinInject<BookDetailViewModel>()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(selectedBook) {
        viewModel.onAction(BookDetailAction.OnSelectedBookChange(selectedBook))
    }

    BookDetailScreen(
        state = state,
        onAction = viewModel::onAction,
        onBackClick = onBackClick
    )
} 