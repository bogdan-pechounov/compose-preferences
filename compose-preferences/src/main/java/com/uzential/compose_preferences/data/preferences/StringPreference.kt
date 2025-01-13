package com.uzential.compose_preferences.data.preferences

import androidx.datastore.preferences.core.stringPreferencesKey
import com.uzential.compose_preferences.data.SingleKeyPreference
import com.uzential.compose_preferences.data.SingleKeyPreferenceWithDefaultValue

open class StringPreference(
    name: String,
    defaultValue: String = ""
): SingleKeyPreferenceWithDefaultValue<String>(stringPreferencesKey(name), defaultValue)

open class StringPreferenceNullable(
    name: String,
): SingleKeyPreference<String>(stringPreferencesKey(name))


