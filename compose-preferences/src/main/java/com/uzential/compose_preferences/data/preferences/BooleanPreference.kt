package com.uzential.compose_preferences.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey
import com.uzential.compose_preferences.data.SingleKeyPreferenceWithDefaultValue

class BooleanPreference(
    name: String,
    defaultValue: Boolean = false,
) : SingleKeyPreferenceWithDefaultValue<Boolean>(booleanPreferencesKey(name), defaultValue)



