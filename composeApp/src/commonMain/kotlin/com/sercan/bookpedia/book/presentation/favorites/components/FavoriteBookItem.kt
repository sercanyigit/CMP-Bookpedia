package com.sercan.bookpedia.book.presentation.favorites.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.core.presentation.utils.Constants

@Composable
fun FavoriteBookItem(
    book: Book,
    onBookClick: (Book) -> Unit,
    onRemoveFromFavorites: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onBookClick(book) }
            .padding(vertical = Constants.UI.SMALL_PADDING.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(Constants.UI.DEFAULT_PADDING.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = book.imageUrl,
                contentDescription = book.title,
                modifier = Modifier
                    .size(80.dp, 120.dp)
                    .clip(RoundedCornerShape(Constants.UI.SMALL_PADDING.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(Constants.UI.SMALL_PADDING.dp)
            ) {
                Text(
                    text = book.title,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = book.authors.joinToString(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        IconButton(
            onClick = { onRemoveFromFavorites(book) }
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Favorilerden Kaldır",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
} 