package com.uzential.compose_preferences.ui.providers

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times

// make sure values are divisible by 4 for consistency
val Int.u: Dp
    get() = this * 4.dp

/**
 * Values for placing and spacing elements
 */
data class PreferenceSpacing(
    val itemMinHeight: Dp = 12.u,
    val itemPadding: PaddingValues = PaddingValues(horizontal = 4.u, vertical = 2.u),
    val iconPadding: PaddingValues = PaddingValues(end = 5.u),
    val actionPadding: PaddingValues = PaddingValues(start = 1.u)
)

val LocalPreferenceSpacing = compositionLocalOf { PreferenceSpacing() }

