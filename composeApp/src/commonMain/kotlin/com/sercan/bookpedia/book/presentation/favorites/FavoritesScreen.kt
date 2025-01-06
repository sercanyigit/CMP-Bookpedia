package com.sercan.bookpedia.book.presentation.favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sercan.bookpedia.book.domain.model.Book
import com.sercan.bookpedia.book.presentation.favorites.components.FavoriteBookItem
import com.sercan.bookpedia.book.presentation.favorites.state.FavoritesState
import com.sercan.bookpedia.core.presentation.components.ConfirmationBottomSheet
import com.sercan.bookpedia.core.presentation.components.LottieAnimationView
import com.sercan.bookpedia.core.presentation.components.ScreenWrapper
import com.sercan.bookpedia.core.presentation.utils.Constants
import com.sercan.bookpedia.core.presentation.utils.defaultAnimation
import org.koin.compose.viewmodel.koinViewModel

@Composable
private fun AnimatedFavoriteBookItem(
    book: Book,
    index: Int,
    onBookClick: (Book) -> Unit,
    onRemoveFromFavorites: (Book) -> Unit,
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
            FavoriteBookItem(
                book = book,
                onBookClick = onBookClick,
                onRemoveFromFavorites = onRemoveFromFavorites,
                modifier = modifier.defaultAnimation()
            )
            Divider(
                modifier = Modifier
                    .padding(horizontal = Constants.UI.DEFAULT_PADDING.dp)
                    .defaultAnimation()
            )
        }
    }
}

@Composable
fun FavoritesScreenRoot(
    viewModel: FavoritesViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    FavoritesScreen(
        state = state,
        onAction = viewModel::onAction,
        onBookClick = onBookClick
    )
}

@Composable
fun FavoritesScreen(
    state: FavoritesState,
    onAction: (FavoritesAction) -> Unit,
    onBookClick: (Book) -> Unit
) {
    var showConfirmation by remember { mutableStateOf<Book?>(null) }

    ScreenWrapper(
        state = state
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = Constants.UI.DEFAULT_PADDING.dp)
        ) {
            Text(
                text = "Favoriler",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(vertical = Constants.UI.DEFAULT_PADDING.dp)
            )

            if (state.books.isEmpty()) {
                EmptyFavoritesState()
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Constants.UI.SMALL_PADDING.dp)
                ) {
                    itemsIndexed(
                        items = state.books,
                        key = { _, book -> book.id }
                    ) { index, book ->
                        AnimatedFavoriteBookItem(
                            book = book,
                            index = index,
                            onBookClick = onBookClick,
                            onRemoveFromFavorites = { showConfirmation = it }
                        )
                    }
                }
            }
        }
    }

    showConfirmation?.let { book ->
        ConfirmationBottomSheet(
            title = "Favorilerden Kaldır",
            description = "${book.title} kitabını favorilerden kaldırmak istediğinize emin misiniz?",
            confirmText = "Kaldır",
            onConfirm = {
                onAction(FavoritesAction.OnRemoveClick(book))
                showConfirmation = null
            },
            onDismiss = { showConfirmation = null }
        )
    }
}

@Composable
private fun EmptyFavoritesState(
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
            file = "favorite.json",
            modifier = Modifier.size(Constants.UI.LOADING_SIZE.dp)
        )
        Spacer(modifier = Modifier.height(Constants.UI.DEFAULT_PADDING.dp))
        Text(
            text = "Henüz Favori Kitabınız Yok",
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(Constants.UI.SMALL_PADDING.dp))
        Text(
            text = "Beğendiğiniz kitapları favorilere ekleyerek\nburada görüntüleyebilirsiniz",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
} 