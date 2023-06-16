package com.uzential.composepreferences.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.uzential.composepreferences.data.compose.defaultValue
import kotlinx.coroutines.flow.Flow

interface Preference<V> {
    val key: Preferences.Key<V>
    val defaultValue: Context.() -> V
    fun value(preferences: Preferences): V? = preferences[key]
    fun valueOrDefault(preferences: Preferences, context: Context): V =
        value(preferences) ?: defaultValue(context)
    fun flow(dataStore: DataStore<Preferences>): Flow<V?>
    fun flowOrDefault(dataStore: DataStore<Preferences>, context: Context): Flow<V>
    suspend fun edit(dataStore: DataStore<Preferences>, newValue: (currentValue: V?) -> V)
}



