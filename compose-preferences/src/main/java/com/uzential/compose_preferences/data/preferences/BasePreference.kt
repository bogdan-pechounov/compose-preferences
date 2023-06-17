package com.uzential.compose_preferences.data.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.uzential.compose_preferences.data.Preference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map


open class BasePreference<V>(
    override val key: Preferences.Key<V>,
    /**
     * Most of the time, the context is not needed
     */
    val defaultValue: V,
    /**
     * Use the context to access resources
     */
    override val defaultValueFromContext: Context.() -> V = { defaultValue }
) : Preference<V> {
    /**
     *  Flow for when the defaultValue is just a constant
     */
    fun flowOrDefault(dataStore: DataStore<Preferences>): Flow<V> =
        dataStore.data.map { value(it) ?: defaultValue }.distinctUntilChanged()
}