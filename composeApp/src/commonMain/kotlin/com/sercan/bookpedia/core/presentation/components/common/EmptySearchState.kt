package com.sercan.bookpedia.core.presentation.components.common

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sercan.bookpedia.core.presentation.components.LottieAnimationView
import com.sercan.bookpedia.core.presentation.utils.Constants

@Composable
fun EmptySearchState(
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
            text = "Kitapları Keşfet",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(Constants.UI.SMALL_PADDING.dp))
        Text(
            text = "Kitap adı, yazar veya ISBN ile arama yapın",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
} 