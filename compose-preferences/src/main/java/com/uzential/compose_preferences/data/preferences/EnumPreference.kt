package com.uzential.compose_preferences.data.preferences

import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.uzential.compose_preferences.data.Preference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

open class EnumPreference<E : Enum<E>>(
    name: String,
    val defaultValue: E,
    val valueOf: (String) -> E
) : Preference<E> {

    private val stringPreference = StringPreference(name, defaultValue = defaultValue.name)

    override fun flow(dataStore: DataStore<Preferences>): Flow<E> {
        return stringPreference.flow(dataStore).map {
            valueOfOrDefault(it)
        }
    }

    override val previewDefaultValue: E
        @Composable
        get() = defaultValue

    override suspend fun edit(
        dataStore: DataStore<Preferences>,
        transform: (currentValue: E?) -> E?
    ) {
        stringPreference.edit(dataStore) { name ->
            transform(name?.let { valueOfOrDefault(it) })?.name
        }
    }

    private fun valueOfOrDefault(name: String) = try {
        valueOf(name)
    } catch (e: Exception) {
        defaultValue
    }
}

open class EnumPreferenceNullable<E : Enum<E>>(
    name: String,
    val defaultValue: E,
    val valueOf: (String) -> E
) : Preference<E> {

    private val stringPreference = StringPreferenceNullable(name)

    override fun flow(dataStore: DataStore<Preferences>): Flow<E?> {
        return stringPreference.flow(dataStore).map {
            it?.let { it1 -> valueOfOrDefault(it1) }
        }
    }

    override val previewDefaultValue: E
        @Composable
        get() = defaultValue

    override suspend fun edit(
        dataStore: DataStore<Preferences>,
        transform: (currentValue: E?) -> E?
    ) {
        stringPreference.edit(dataStore) { name ->
            transform(name?.let { valueOfOrDefault(it) })?.name
        }
    }

    private fun valueOfOrDefault(name: String) = try {
        valueOf(name)
    } catch (e: Exception) {
        defaultValue
    }
}

inline fun <reified T : Enum<T>> valueOf(type: String): T? {
    return try {
        java.lang.Enum.valueOf(T::class.java, type)
    } catch (e: IllegalArgumentException) {
        null
    }
}
