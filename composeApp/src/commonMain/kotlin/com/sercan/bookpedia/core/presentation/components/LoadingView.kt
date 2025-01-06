package com.sercan.bookpedia.core.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sercan.bookpedia.core.presentation.utils.Constants

@Composable
fun LoadingView(
    modifier: Modifier = Modifier,
    message: String = "Yükleniyor..."
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Constants.UI.DEFAULT_PADDING.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimationView(
            file = "loading.json",
            modifier = Modifier.size(Constants.UI.LOADING_SIZE.dp)
        )
        
        Spacer(modifier = Modifier.height(Constants.UI.SMALL_PADDING.dp))
        
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = Constants.Search.ALPHA_DESCRIPTION
            )
        )
    }
} 