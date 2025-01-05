package com.sercan.bookpedia.book.presentation.book_list.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.bookpedia.book.domain.Book

@Composable
private fun AnimatedBookListItem(
    book: Book,
    index: Int,
    onClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
    }
    
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn() + slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            initialOffsetY = { it * (index + 1) }
        )
    ) {
        Column {
            BookListItem(
                book = book,
                onClick = { onClick(book) }
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .animateContentSize(),
                thickness = 0.5.dp
            )
        }
    }
}

@Composable
fun BookList(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier,
    scrollState: LazyListState = rememberLazyListState()
) {
    val uniqueBooks = remember(books) {
        books.distinctBy { it.id }
    }
    
    LazyColumn(
        modifier = modifier,
        state = scrollState,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        itemsIndexed(
            items = uniqueBooks,
            key = { index, book -> "${book.id}_$index" }
        ) { index, book ->
            AnimatedBookListItem(
                book = book,
                index = index,
                onClick = onBookClick
            )
        }
    }
}