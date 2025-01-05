package com.sercan.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.bookpedia.book.domain.Book

@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(
            items = books,
            key = { it.id }
        ) { book ->
            BookListItem(
                book = book,
                onClick = { onBookClick(book) }
            )
        }
    }
}