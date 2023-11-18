package com.uzential.compose_preferences.ui.providers

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow

val Context.settingsDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val dummyDataStore = object : DataStore<Preferences> {
    val msg = "No datastore provided: use `DataStoreProvider {}`"
    override val data: Flow<Preferences>
        get() = throw RuntimeException(msg)

    override suspend fun updateData(transform: suspend (t: Preferences) -> Preferences): Preferences {
        throw RuntimeException(msg)
    }
}

val LocalDataStore = staticCompositionLocalOf { dummyDataStore }

@Composable
fun DataStoreProvider(
    dataStore: DataStore<Preferences> = LocalContext.current.settingsDataStore,
    content: @Composable () -> Unit,
) {
    CompositionLocalProvider(LocalDataStore.provides(dataStore)) {
        content()
    }
}
