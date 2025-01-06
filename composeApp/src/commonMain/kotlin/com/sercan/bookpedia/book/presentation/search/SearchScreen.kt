package com.sercan.bookpedia.book.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sercan.bookpedia.book.domain.Book
import com.sercan.bookpedia.book.presentation.book_list.components.BookList
import com.sercan.bookpedia.book.presentation.book_list.components.BookSearchBar
import com.sercan.bookpedia.core.presentation.components.LottieAnimationView
import com.sercan.bookpedia.core.presentation.components.common.EmptySearchState
import com.sercan.bookpedia.core.presentation.components.common.ScreenWrapper
import com.sercan.bookpedia.core.presentation.utils.Constants
import com.sercan.bookpedia.core.presentation.utils.defaultAnimation
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchScreenRoot(
    viewModel: SearchViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    SearchScreen(
        state = state,
        onAction = viewModel::onAction,
        onBookClick = onBookClick
    )
}

@Composable
fun SearchScreen(
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    onBookClick: (Book) -> Unit
) {
    ScreenWrapper(
        state = state,
        onRetry = { onAction(SearchAction.OnRetry) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = Constants.UI.DEFAULT_PADDING.dp)
        ) {
            Text(
                text = "Arama",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier
                    .padding(vertical = Constants.UI.DEFAULT_PADDING.dp)
                    .defaultAnimation()
            )

            BookSearchBar(
                searchQuery = state.searchQuery,
                onSearchQueryChange = { onAction(SearchAction.OnQueryChange(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .defaultAnimation()
            )

            Spacer(modifier = Modifier.height(Constants.UI.DEFAULT_PADDING.dp))

            if (state.searchQuery.isEmpty()) {
                EmptySearchState()
            } else {
                AnimatedVisibility(
                    visible = state.searchResults.isNotEmpty(),
                    enter = fadeIn() + expandVertically(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                ) {
                    BookList(
                        books = state.searchResults,
                        onBookClick = onBookClick,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptySearchState(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Constants.UI.DEFAULT_PADDING.dp * 2),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LottieAnimationView(
            file = "search.json",
            modifier = Modifier.size(Constants.UI.LOADING_SIZE.dp)
        )
        Spacer(modifier = Modifier.height(Constants.UI.DEFAULT_PADDING.dp))
        Text(
            text = "Kitap Ara",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(Constants.UI.SMALL_PADDING.dp))
        Text(
            text = "Aramak istediğiniz kitabın adını veya\nyazarını yazarak arama yapabilirsiniz",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
} 