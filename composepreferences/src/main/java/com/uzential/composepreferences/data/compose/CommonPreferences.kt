package com.uzential.composepreferences.data.compose

import android.content.Context
import android.content.res.Configuration
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import com.uzential.composepreferences.data.BooleanPreference

class DarkThemePreference(keyName: String = "dark_theme") : BooleanPreference(keyName) {
    override val defaultValue: Context.() -> Boolean
        get() = {
            isSystemInDarkTheme(resources.configuration)
        }
}

fun isSystemInDarkTheme(configuration: Configuration): Boolean {
    return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
}

// TODO languages