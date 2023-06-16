package com.uzential.composepreferences.ui

import android.content.Context
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    content: PreferencesScope.() -> Unit
) {
    //PreferencesProvider(dataStore = dataStore) {
        LazyColumn(modifier = modifier) {
            content(PreferencesScopeImpl(this))
        }
    //}
}

/**
 * Scope used to add functions to LazyListScope without affecting other LazyColumns
 */
interface PreferencesScope : LazyListScope
private class PreferencesScopeImpl(lazyListScope: LazyListScope): PreferencesScope, LazyListScope by lazyListScope


