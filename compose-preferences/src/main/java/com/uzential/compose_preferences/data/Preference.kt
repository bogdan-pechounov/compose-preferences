package com.uzential.compose_preferences.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

interface Preference<V> {
    val key: Preferences.Key<V>
    val defaultValue: Context.() -> V

    fun value(preferences: Preferences): V? = preferences[key]
    fun valueOrDefault(preferences: Preferences, context: Context): V =
        value(preferences) ?: defaultValue(context)

    fun flow(dataStore: DataStore<Preferences>): Flow<V?> =
        dataStore.data.map { value(it) }.distinctUntilChanged()
    fun flowOrDefault(dataStore: DataStore<Preferences>, context: Context): Flow<V> =
        dataStore.data.map { valueOrDefault(it, context) }.distinctUntilChanged()

    suspend fun edit(dataStore: DataStore<Preferences>, newValue: (currentValue: V?) -> V) {
        dataStore.edit {
            it[key] = newValue(value(it))
        }
    }
}



