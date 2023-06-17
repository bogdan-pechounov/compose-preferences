package com.uzential.compose_preferences.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.uzential.compose_preferences.ui.providers.Spacing

/**
 * A preference screen is simply a LazyColumn with an extended scope.
 */
@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    content: PreferencesScope.() -> Unit
) {
    LazyColumn(modifier = modifier) {
        content(PreferencesScopeImpl(this))
    }
}

/**
 * Scope used to add functions to LazyListScope without affecting other LazyColumns
 */
interface PreferencesScope : LazyListScope
private class PreferencesScopeImpl(lazyListScope: LazyListScope) : PreferencesScope,
    LazyListScope by lazyListScope


