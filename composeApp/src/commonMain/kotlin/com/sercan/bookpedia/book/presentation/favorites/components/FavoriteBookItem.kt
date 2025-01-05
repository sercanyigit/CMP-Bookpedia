package com.sercan.bookpedia.book.presentation.favorites.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sercan.bookpedia.book.domain.Book

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteBookItem(
    book: Book,
    onBookClick: (Book) -> Unit,
    onRemoveFromFavorites: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    var showConfirmationSheet by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        onClick = { onBookClick(book) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = book.authors.firstOrNull() ?: "Bilinmeyen Yazar",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            IconButton(
                onClick = { showConfirmationSheet = true }
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorilerden çıkar",
                    tint = Color.Red
                )
            }
        }
    }

    if (showConfirmationSheet) {
        ModalBottomSheet(
            onDismissRequest = { showConfirmationSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Favorilerden Çıkar",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "\"${book.title}\" kitabını favorilerinizden çıkarmak istediğinize emin misiniz?",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    OutlinedButton(
                        onClick = { showConfirmationSheet = false },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Vazgeç")
                    }
                    Button(
                        onClick = {
                            onRemoveFromFavorites(book)
                            showConfirmationSheet = false
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Çıkar")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
} 