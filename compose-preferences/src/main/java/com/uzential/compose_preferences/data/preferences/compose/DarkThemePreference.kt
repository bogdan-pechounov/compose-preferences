package com.uzential.compose_preferences.data.preferences.compose

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.uzential.compose_preferences.data.Preference
import com.uzential.compose_preferences.data.Result
import com.uzential.compose_preferences.data.SingleKeyPreference

class DarkThemePreference(
    name: String = "dark_theme",
) : SingleKeyPreference<Boolean>(booleanPreferencesKey(name)) {
    override val value: Boolean?
        @Composable
        get() = when (val result = result) {
            is Result.Loaded -> result.value ?: isSystemInDarkTheme()
            Result.Loading -> null
        }

    override val previewDefaultValue: Boolean
        @Composable
        get() = isSystemInDarkTheme()


    /**
     * Get actual value stored
     */
    fun toActual() = object : Preference<Boolean> by this {
        override val value: Boolean?
            @Composable
            get() = super.value
    }
}