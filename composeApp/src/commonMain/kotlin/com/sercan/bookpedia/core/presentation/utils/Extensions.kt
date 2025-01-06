package com.sercan.bookpedia.core.presentation.utils

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.unit.dp

fun Modifier.defaultPadding() = this.padding(Constants.UI.DEFAULT_PADDING.dp)

fun Modifier.smallPadding() = this.padding(Constants.UI.SMALL_PADDING.dp)

fun Modifier.defaultAnimation() = composed {
    this.animateContentSize(
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessLow
        )
    )
} 