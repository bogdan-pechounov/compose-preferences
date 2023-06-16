package com.uzential.composepreferences.ui.providers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

// make sure values are divisible by 4 for consistency
val Int.unit: Dp
    get() = this * 4.dp

val Int.bigUnit : Dp
    get() = this * 16.dp

/**
 * Values for placing and spacing elements
 */
data class Spacing(
    val itemMinHeight: Dp = 3.bigUnit,
    val itemPadding: PaddingValues = PaddingValues(horizontal = 1.bigUnit, vertical = 2.unit),
    val iconPadding: PaddingValues = PaddingValues(end = 1.bigUnit),
)

val LocalSpacing = compositionLocalOf { Spacing() }

@Composable
fun ThemeProvider(
    spacing: Spacing = Spacing(),
    content: @Composable () -> Unit
) =
    CompositionLocalProvider(LocalSpacing.provides(spacing)) { content() }