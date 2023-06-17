package com.uzential.compose_preferences.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Convenient methods to get data from a DataStore
 */
interface Preference<V> {
    /**
     * Key to get a preference
     */
    val key: Preferences.Key<V>
    /**
     * A context is necessary to have a default value that matches with the current theme or language
     */
    val defaultValueFromContext: Context.() -> V

    // value
    fun value(preferences: Preferences): V? = preferences[key]
    fun valueOrDefault(preferences: Preferences, context: Context): V =
        value(preferences) ?: defaultValueFromContext(context)

    // read
    fun flow(dataStore: DataStore<Preferences>): Flow<V?> =
        dataStore.data.map { value(it) }.distinctUntilChanged()
    fun flowOrDefault(dataStore: DataStore<Preferences>, context: Context): Flow<V> =
        dataStore.data.map { valueOrDefault(it, context) }.distinctUntilChanged()

    // write
    suspend fun edit(dataStore: DataStore<Preferences>, newValue: (currentValue: V?) -> V) {
        dataStore.edit {
            it[key] = newValue(value(it))
        }
    }
}

