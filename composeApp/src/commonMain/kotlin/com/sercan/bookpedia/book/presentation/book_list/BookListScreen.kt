package com.sercan.bookpedia.book.presentation.book_list

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cmp_bookpedia.composeapp.generated.resources.Res
import cmp_bookpedia.composeapp.generated.resources.ic_night
import cmp_bookpedia.composeapp.generated.resources.ic_sunny
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.sercan.bookpedia.book.domain.model.Book
import com.sercan.bookpedia.book.presentation.book_list.components.BookList
import com.sercan.bookpedia.book.presentation.book_list.state.BookListState
import com.sercan.bookpedia.core.presentation.components.PulseAnimation
import com.sercan.bookpedia.core.presentation.components.ScreenWrapper
import com.sercan.bookpedia.core.presentation.utils.Constants
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RecommendedBooksPager(
    books: List<Book>,
    onBookClick: (Book) -> Unit,
    modifier: Modifier = Modifier
) {
    val groupedBooks = books.chunked(3)
    val pagerState = rememberPagerState(pageCount = { groupedBooks.size })
    
    // Otomatik geçiş için LaunchedEffect
    LaunchedEffect(Unit) {
        while (true) {
            delay(Constants.Animation.PAGER_DELAY.toLong())
            val nextPage = (pagerState.currentPage + 1) % groupedBooks.size
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) { page ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                groupedBooks[page].forEach { book ->
                    AnimatedBookCoverItem(
                        book = book,
                        onClick = { onBookClick(book) },
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 4.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Page indicator
        Row(
            Modifier
                .height(Constants.UI.PAGE_INDICATOR_HEIGHT.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color = if (pagerState.currentPage == iteration) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.primary.copy(alpha = Constants.Search.ALPHA_DISABLED)
                }
                
                Box(
                    modifier = Modifier
                        .padding(Constants.UI.INDICATOR_SPACING.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(
                            if (pagerState.currentPage == iteration) 
                                Constants.UI.INDICATOR_SIZE.dp 
                            else 
                                Constants.UI.INDICATOR_SMALL_SIZE.dp
                        )
                        .animateContentSize(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
                )
            }
        }
    }
}

@Composable
fun BookListScreenRoot(
    viewModel: BookListViewModel = koinViewModel(),
    onBookClick: (Book) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isDarkMode by viewModel.isDarkMode.collectAsStateWithLifecycle()
    
    BookListScreen(
        state = state,
        isDarkMode = isDarkMode,
        onAction = viewModel::onAction,
        onBookClick = onBookClick
    )
}

@Composable
fun BookListScreen(
    state: BookListState,
    isDarkMode: Boolean,
    onAction: (BookListAction) -> Unit,
    onBookClick: (Book) -> Unit
) {
    ScreenWrapper(state = state) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Constants.UI.DEFAULT_PADDING.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Kitaplık",
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
                
                IconButton(
                    onClick = { onAction(BookListAction.OnThemeToggle) }
                ) {
                    Image(
                        painter = painterResource(if (isDarkMode) Res.drawable.ic_sunny else Res.drawable.ic_night),
                        contentDescription = if (isDarkMode) "Açık Tema" else "Koyu Tema",
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
            
            if (state.searchResults.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Constants.UI.DEFAULT_PADDING.dp)
                ) {
                    Text(
                        text = "Öneriler",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = Constants.UI.SMALL_PADDING.dp)
                    )
                    
                    RecommendedBooksPager(
                        books = state.searchResults.take(9),
                        onBookClick = onBookClick
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Constants.UI.DEFAULT_PADDING.dp)
                ) {
                    Text(
                        text = "Sizin İçin",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = Constants.UI.SMALL_PADDING.dp)
                    )

                    BookList(
                        books = state.searchResults,
                        onBookClick = onBookClick
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimatedBookCoverItem(
    book: Book,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isHovered by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Surface(
        modifier = modifier
            .width(Constants.UI.BOOK_COVER_WIDTH.dp)
            .height(Constants.UI.BOOK_COVER_HEIGHT.dp)
            .scale(scale)
            .clickable {
                isHovered = true
                onClick()
            },
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = if (isHovered) 8.dp else 4.dp
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            var imageState by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }
            
            AsyncImage(
                model = book.imageUrl,
                contentDescription = book.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                onState = { imageState = it }
            )
            
            if (imageState is AsyncImagePainter.State.Loading || imageState is AsyncImagePainter.State.Error) {
                PulseAnimation(
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    LaunchedEffect(isHovered) {
        if (isHovered) {
            delay(150)
            isHovered = false
        }
    }
}