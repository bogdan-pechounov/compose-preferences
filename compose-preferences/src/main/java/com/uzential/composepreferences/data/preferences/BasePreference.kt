package com.uzential.composepreferences.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.uzential.composepreferences.data.Preference
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

/**
 * Base class for preferences
 * @param key
 * @param defaultValue default value using a context to access resources if necessary
 */
open class BasePreference<V>(
    override val key: Preferences.Key<V>, override val defaultValue: Context.() -> V
) : Preference<V> {

    override fun flow(dataStore: DataStore<Preferences>) =
        dataStore.data.map { value(it) }.distinctUntilChanged()

    override fun flowOrDefault(dataStore: DataStore<Preferences>, context: Context) =
        dataStore.data.map { valueOrDefault(it, context) }.distinctUntilChanged()

    override suspend fun edit(
        dataStore: DataStore<Preferences>, newValue: (currentValue: V?) -> V
    ) {
        dataStore.edit {
            it[key] = newValue(value(it))
        }
    }
}