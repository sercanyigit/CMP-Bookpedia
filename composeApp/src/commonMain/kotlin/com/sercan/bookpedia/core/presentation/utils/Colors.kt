package com.sercan.bookpedia.core.presentation.utils

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

val DarkBlue = Color(0xFF0B405E)
val DesertWhite = Color(0xFFF7F7F7)
val SandYellow = Color(0xFFFFBD64)
val LightBlue = Color(0xFF9AD9FF)

val LightColors = lightColorScheme(
    primary = DarkBlue,
    secondary = SandYellow,
    tertiary = LightBlue,
    background = DesertWhite,
    surface = DesertWhite,
    onPrimary = DesertWhite,
    onSecondary = DarkBlue,
    onTertiary = DarkBlue,
    onBackground = DarkBlue,
    onSurface = DarkBlue,
)

val DarkColors = darkColorScheme(
    primary = LightBlue,
    secondary = SandYellow,
    tertiary = DarkBlue,
    background = DarkBlue,
    surface = DarkBlue,
    onPrimary = DarkBlue,
    onSecondary = DesertWhite,
    onTertiary = DesertWhite,
    onBackground = DesertWhite,
    onSurface = DesertWhite,
)