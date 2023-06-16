package com.uzential.composepreferences.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.uzential.composepreferences.ui.providers.Spacing
import com.uzential.composepreferences.ui.providers.ThemeProvider


@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    spacing: Spacing = Spacing(),
    content: PreferencesScope.() -> Unit
) {
    ThemeProvider(spacing = spacing) {
        LazyColumn(modifier = modifier) {
            content(PreferencesScopeImpl(this))
        }
    }
}

/**
 * Scope used to add functions to LazyListScope without affecting other LazyColumns
 */
interface PreferencesScope : LazyListScope
private class PreferencesScopeImpl(lazyListScope: LazyListScope): PreferencesScope, LazyListScope by lazyListScope


