package com.sercan.bookpedia.book.presentation.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sercan.bookpedia.core.presentation.components.LottieAnimationView
import com.sercan.bookpedia.core.presentation.components.common.ScreenWrapper
import com.sercan.bookpedia.core.presentation.utils.Constants
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingScreenRoot(
    viewModel: OnboardingViewModel = koinViewModel(),
    onFinish: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    
    OnboardingScreen(
        state = state,
        onAction = viewModel::onAction,
        onFinish = onFinish
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    state: OnboardingState,
    onAction: (OnboardingAction) -> Unit,
    onFinish: () -> Unit
) {
    val pagerState = rememberPagerState { OnboardingViewModel.onboardingPages.size }

    LaunchedEffect(state.currentPage) {
        pagerState.animateScrollToPage(state.currentPage)
    }

    LaunchedEffect(pagerState.currentPage) {
        onAction(OnboardingAction.OnPageChanged(pagerState.currentPage))
    }

    ScreenWrapper(
        state = state
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                OnboardingPage(page = OnboardingViewModel.onboardingPages[page])
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = Constants.UI.DEFAULT_PADDING.dp)
                    .padding(bottom = Constants.UI.DEFAULT_PADDING.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (state.currentPage > 0) {
                    IconButton(
                        onClick = { onAction(OnboardingAction.PreviousPage) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Geri"
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.size(48.dp))
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(Constants.UI.SMALL_PADDING.dp)
                ) {
                    repeat(OnboardingViewModel.onboardingPages.size) { index ->
                        Box(
                            modifier = Modifier
                                .size(
                                    width = if (index == state.currentPage) 24.dp else 8.dp,
                                    height = 8.dp
                                )
                                .clip(CircleShape)
                                .background(
                                    if (index == state.currentPage)
                                        MaterialTheme.colorScheme.primary
                                    else
                                        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                                )
                                .animateContentSize()
                        )
                    }
                }

                if (state.isLastPage) {
                    Button(
                        onClick = onFinish,
                        contentPadding = PaddingValues(horizontal = 24.dp)
                    ) {
                        Text("Başla")
                    }
                } else {
                    IconButton(
                        onClick = { onAction(OnboardingAction.NextPage) }
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "İleri"
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun OnboardingPage(
    page: OnboardingPage,
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
            )
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(Constants.UI.DEFAULT_PADDING.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = page.title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center
            )
            
            Spacer(modifier = Modifier.height(Constants.UI.SMALL_PADDING.dp))
            
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(Constants.UI.DEFAULT_PADDING.dp))
            
            LottieAnimationView(
                file = page.lottieRes,
                modifier = Modifier.size(280.dp)
            )
        }
    }
} 