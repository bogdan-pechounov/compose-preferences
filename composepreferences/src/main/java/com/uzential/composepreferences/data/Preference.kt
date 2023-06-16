package com.uzential.composepreferences.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Base class for preferences
 * @param key
 * @param defaultValue default value using a context to access resources if necessary
 */
open class Preference<V>(val key: Preferences.Key<V>, open val defaultValue: Context.() -> V) {
    fun value(preferences: Preferences) = preferences[key]
    fun valueOrDefault(preferences: Preferences, context: Context) =
        value(preferences) ?: defaultValue(context)

    // read
    fun flow(dataStore: DataStore<Preferences>) =
        dataStore.data.map { value(it) }.distinctUntilChanged()

    fun flowOrDefault(dataStore: DataStore<Preferences>, context: Context) =
        dataStore.data.map { valueOrDefault(it, context) }.distinctUntilChanged()

    // write
    suspend fun edit(dataStore: DataStore<Preferences>, callback: (currentValue: V?) -> V) =
        dataStore.edit {
            it[key] = callback(value(it))
        }
}

open class BooleanPreference(keyName: String, defaultValue: Boolean = false) :
    Preference<Boolean>(
        booleanPreferencesKey(keyName), { defaultValue }
    )