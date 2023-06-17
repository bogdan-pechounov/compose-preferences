package com.uzential.compose_preferences.data.preferences

import androidx.datastore.preferences.core.intPreferencesKey

open class IntPreference(keyName: String, defaultValue: Int) :
    BasePreference<Int>(intPreferencesKey(keyName), { defaultValue })