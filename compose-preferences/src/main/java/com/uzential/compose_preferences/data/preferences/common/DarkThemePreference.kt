package com.uzential.compose_preferences.data.preferences.common

import android.content.Context
import android.content.res.Configuration
import com.uzential.compose_preferences.data.preferences.BooleanPreference

class DarkThemePreference(keyName: String = "dark_theme") : BooleanPreference(keyName) {
    override val defaultValueFromContext: Context.() -> Boolean
        get() = {
            isSystemInDarkTheme(resources.configuration)
        }
}

fun isSystemInDarkTheme(configuration: Configuration): Boolean {
    return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}

// TODO languages