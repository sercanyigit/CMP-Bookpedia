package com.sercan.bookpedia.book.presentation.book_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

@Composable
fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
    onBackClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if(state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        state.book?.let { book ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onBackClick
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Go back"
                        )
                    }
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = book.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(
                        onClick = {
                            onAction(BookDetailAction.OnFavoriteClick)
                        }
                    ) {
                        Icon(
                            imageVector = if(state.isFavorite) {
                                Icons.Default.Favorite
                            } else Icons.Default.FavoriteBorder,
                            contentDescription = if(state.isFavorite) {
                                "Remove from favorites"
                            } else "Add to favorites",
                            tint = if(state.isFavorite) {
                                Color.Red
                            } else MaterialTheme.colorScheme.onBackground
                        )
                    }
                }

                AsyncImage(
                    model = book.imageUrl,
                    contentDescription = book.title,
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = book.authors.get(0),
                        fontSize = 16.sp,
                        fontStyle = FontStyle.Italic
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = book.description ?: "No description available",
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}