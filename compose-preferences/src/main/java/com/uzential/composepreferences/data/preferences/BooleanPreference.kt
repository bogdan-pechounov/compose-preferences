package com.uzential.composepreferences.data.preferences

import androidx.datastore.preferences.core.booleanPreferencesKey

open class BooleanPreference(keyName: String, defaultValue: Boolean = false) :
    BasePreference<Boolean>(booleanPreferencesKey(keyName), { defaultValue })