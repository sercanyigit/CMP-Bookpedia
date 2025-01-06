package com.sercan.bookpedia.book.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.presentation.book_list.components.BookList
import com.sercan.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.sercan.bookpedia.core.presentation.components.LottieAnimationView
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreenRoot(
    viewModel: SearchViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        state = state,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        onBookClick = onBookClick,
        onFavoriteClick = viewModel::toggleFavorite
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    onSearchQueryChange: (String) -> Unit,
    onBookClick: (Book) -> Unit,
    onFavoriteClick: (Book) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Arama",
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        BookSearchBar(
            searchQuery = state.searchQuery,
            onSearchQueryChange = onSearchQueryChange,
            onImeSearch = {},
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if(state.isLoading) {
                Column (
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LottieAnimationView(
                        file = "loading.json",
                        modifier = Modifier.size(250.dp)
                    )
                    Text("Yükleniyor...")
                }
            } else if (state.searchResults.isNotEmpty()) {
                BookList(
                    books = state.searchResults,
                    onBookClick = onBookClick,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                // Initial search design or no results
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    LottieAnimationView(
                        file = "search.json",
                        modifier = Modifier.size(250.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Kitapları Keşfet",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Kitap adı, yazar veya ISBN ile arama yapın",
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
} 