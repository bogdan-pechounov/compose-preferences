package com.uzential.compose_preferences.data

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uzential.compose_preferences.data.compose.inPreviewMode
import com.uzential.compose_preferences.ui.providers.LocalDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

sealed class Result<out V> {
    object Loading : Result<Nothing>()
    data class Loaded<V>(val value: V) : Result<V>()
}

interface Preference<V> {

    fun flow(dataStore: DataStore<Preferences>): Flow<V?>
    suspend fun edit(dataStore: DataStore<Preferences>, transform: (currentValue: V?) -> V?)

    fun flowResult(dataStore: DataStore<Preferences>) = flow(dataStore).map { Result.Loaded(it) }

    val result: Result<V?>
        @Composable
        get() {
            if (inPreviewMode) {
                return Result.Loaded(previewDefaultValue)
            }
            return flowResult(LocalDataStore.current)
                .collectAsStateWithLifecycle(initialValue = Result.Loading)
                .value
        }

    val value: V?
        @Composable
        get() {
            if (inPreviewMode) {
                return previewDefaultValue
            }
            return flow(LocalDataStore.current)
                .collectAsStateWithLifecycle(initialValue = null)
                .value
        }

    @Composable
    fun setValue(): (newValue: V?) -> Unit {
        if (inPreviewMode) {
            return {}
        }

        val scope = rememberCoroutineScope()
        val dataStore = LocalDataStore.current
        return { newValue ->
            scope.launch {
                edit(dataStore) {
                    newValue
                }
            }
        }
    }

    @get:Composable
    val previewDefaultValue: V?
}


abstract class SingleKeyPreference<V>(
    val key: Preferences.Key<V>,
) : Preference<V> {

    override fun flow(dataStore: DataStore<Preferences>): Flow<V?> {
        return dataStore.data
            .map { preferences ->
                preferences[key]
            }
            .distinctUntilChanged()
    }



    override suspend fun edit(
        dataStore: DataStore<Preferences>,
        transform: (currentValue: V?) -> V?,
    ) {
        dataStore.edit { preferences ->
            val newValue = transform(preferences[key])
            if (newValue != null) {
                preferences[key] = newValue
            } else {
                preferences.remove(key)
            }
        }
    }
}

open class SingleKeyPreferenceWithDefaultValue<V>(
    val key: Preferences.Key<V>,
    val defaultValue: V,
) : Preference<V> {

    override fun flow(dataStore: DataStore<Preferences>): Flow<V> {
        return dataStore.data
            .map { preferences ->
                preferences[key] ?: defaultValue
            }
            .distinctUntilChanged()
    }

    override suspend fun edit(
        dataStore: DataStore<Preferences>,
        transform: (currentValue: V?) -> V?,
    ) {
        dataStore.edit { preferences ->
            val newValue = transform(preferences[key] ?: defaultValue)
            if (newValue != null) {
                preferences[key] = newValue
            } else {
                preferences.remove(key)
            }
        }
    }

    override val previewDefaultValue: V
        @Composable
        get() = defaultValue
}