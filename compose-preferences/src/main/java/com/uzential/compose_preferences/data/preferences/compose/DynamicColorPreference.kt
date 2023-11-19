package com.uzential.compose_preferences.data.preferences.compose

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.booleanPreferencesKey
import com.uzential.compose_preferences.data.Preference
import com.uzential.compose_preferences.data.SingleKeyPreferenceWithDefaultValue

private val isDynamicColorSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

class DynamicColorPreference(
    name: String = "dynamic_color",
    defaultValue: Boolean = true
) : SingleKeyPreferenceWithDefaultValue<Boolean>(booleanPreferencesKey(name), defaultValue) {

    fun hideIfNotSupported() = object : Preference<Boolean> by this {
        override val value: Boolean?
            @Composable
            get() = if(isDynamicColorSupported) { super.value } else { null }
    }
}