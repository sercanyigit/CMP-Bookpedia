package com.sercan.bookpedia.core.presentation.components.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.sercan.bookpedia.core.presentation.base.UiState

@Composable
fun ScreenWrapper(
    modifier: Modifier = Modifier,
    state: UiState,
    onRetry: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        when {
            state.isLoading -> LoadingView()
            state.errorMessage != null -> ErrorView(
                message = state.errorMessage.toString(),
                onRetry = onRetry
            )
            else -> content()
        }
    }
} 