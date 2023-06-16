package com.uzential.composepreferences.ui.providers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

/**
 * Default data store
 */
val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

/**
 * Dummy datastore for compositionLocalOf
 */
val dummyDataStore = object : DataStore<Preferences> {
    override val data: Flow<Preferences>
        get() = TODO("Not yet implemented")

    override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
        TODO("Not yet implemented")
    }
}

/**
 * CompositionLocal providing a datastore
 */
val LocalDataStore = compositionLocalOf { dummyDataStore } // TODO use static?

@Composable
fun DataStoreProvider(
    dataStore: DataStore<Preferences> = LocalContext.current.settingsDataStore,
    content: @Composable () -> Unit
) =
    CompositionLocalProvider(LocalDataStore.provides(dataStore)) { content() }
