package com.sercan.bookpedia.book.presentation.book_list.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sercan.bookpedia.core.presentation.utils.Constants

@Composable
fun BookSearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onSearchQueryChange,
        modifier = modifier
            .fillMaxWidth()
            .height(Constants.UI.SEARCH_BAR_HEIGHT.dp),
        placeholder = {
            Text(
                text = "Kitap, yazar veya ISBN ara",
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface.copy(
                        alpha = Constants.Search.ALPHA_PLACEHOLDER
                    )
                )
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Ara",
                tint = MaterialTheme.colorScheme.onSurface.copy(
                    alpha = Constants.Search.ALPHA_ICON
                )
            )
        },
        singleLine = true,
        shape = RoundedCornerShape(Constants.UI.SEARCH_BAR_CORNER_RADIUS.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(
                alpha = Constants.Search.ALPHA_BORDER
            )
        )
    )
}