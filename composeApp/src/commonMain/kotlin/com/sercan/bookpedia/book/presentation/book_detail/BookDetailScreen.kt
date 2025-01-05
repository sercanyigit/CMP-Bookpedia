package com.sercan.bookpedia.book.presentation.book_detail

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter

@Composable
fun BookDetailScreen(
    state: BookDetailState,
    onAction: (BookDetailAction) -> Unit,
    onBackClick: () -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    
    LaunchedEffect(Unit) {
        visible = true
    }

    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = tween(500, easing = EaseOutCirc)
        ) + slideInVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            initialOffsetY = { it / 2 }
        ),
        exit = fadeOut(
            animationSpec = tween(300)
        ) + slideOutVertically(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessLow
            ),
            targetOffsetY = { it }
        )
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
                                contentDescription = "Geri"
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
                                    "Favorilerden çıkar"
                                } else "Favorilere ekle",
                                tint = if(state.isFavorite) {
                                    Color.Red
                                } else MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }

                    var imageState by remember { mutableStateOf<AsyncImagePainter.State>(AsyncImagePainter.State.Empty) }
                    
                    AsyncImage(
                        model = book.imageUrl,
                        contentDescription = book.title,
                        contentScale = ContentScale.FillWidth,
                        onState = { imageState = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .graphicsLayer {
                                alpha = if (imageState is AsyncImagePainter.State.Success) 1f else 0f
                                scaleX = if (imageState is AsyncImagePainter.State.Success) 1f else 0.8f
                                scaleY = if (imageState is AsyncImagePainter.State.Success) 1f else 0.8f
                            }
                            .animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = book.authors.firstOrNull() ?: "",
                            fontSize = 16.sp,
                            fontStyle = FontStyle.Italic
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = book.description ?: "Açıklama bulunmuyor",
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}