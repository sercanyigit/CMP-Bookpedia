package com.sercan.bookpedia.core.presentation.components

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import com.sercan.bookpedia.core.navigation.Route

@Composable
fun BottomBar(
    currentRoute: Route,
    onNavigate: (Route) -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(
            animationSpec = tween(500, easing = EaseOutCirc)
        ) + slideInVertically(
            animationSpec = tween(500, easing = EaseOutCirc),
            initialOffsetY = { it }
        ),
        exit = fadeOut(
            animationSpec = tween(500, easing = EaseInCirc)
        ) + slideOutVertically(
            animationSpec = tween(500, easing = EaseInCirc),
            targetOffsetY = { it }
        )
    ) {
        NavigationBar(
            modifier = modifier
        ) {
            val items = listOf(
                Triple(Route.Home, Icons.Default.Home, "Ana Sayfa"),
                Triple(Route.Search, Icons.Default.Search, "Arama"),
                Triple(Route.Favorites, Icons.Default.Favorite, "Favoriler")
            )

            items.forEach { (route, icon, label) ->
                val selected = currentRoute::class == route::class
                NavigationBarItem(
                    selected = selected,
                    onClick = { onNavigate(route) },
                    icon = {
                        AnimatedContent(
                            targetState = selected,
                            transitionSpec = {
                                if (targetState) {
                                    (scaleIn(
                                        animationSpec = tween(300),
                                        transformOrigin = TransformOrigin(0.5f, 0.5f)
                                    ) + fadeIn(tween(300))).togetherWith(
                                        scaleOut(
                                            animationSpec = tween(300),
                                            transformOrigin = TransformOrigin(0.5f, 0.5f)
                                        ) + fadeOut(tween(300))
                                    )
                                } else {
                                    fadeIn(tween(300)).togetherWith(fadeOut(tween(300)))
                                }
                            }
                        ) { isSelected ->
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                modifier = Modifier.animateContentSize(
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioLowBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            )
                        }
                    },
                    label = { 
                        Text(
                            text = label,
                            modifier = Modifier.animateContentSize(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioLowBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )
                        )
                    }
                )
            }
        }
    }
} 