package com.uzential.compose_preferences.data.preferences

import androidx.compose.runtime.Composable
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import com.uzential.compose_preferences.data.SingleKeyPreference
import com.uzential.compose_preferences.data.SingleKeyPreferenceWithDefaultValue

open class IntPreference(
    name: String,
    defaultValue: Int = 0
): SingleKeyPreferenceWithDefaultValue<Int>(intPreferencesKey(name), defaultValue)

open class FloatPreference(
    name: String,
    defaultValue: Float = 0f
): SingleKeyPreferenceWithDefaultValue<Float>(floatPreferencesKey(name), defaultValue)

open class IntPreferenceNullable(
    name: String
): SingleKeyPreference<Int>(intPreferencesKey(name))


open class FloatPreferenceNullable(
    name: String,
): SingleKeyPreference<Float>(floatPreferencesKey(name)){
    override val previewDefaultValue: Float?
        @Composable
        get() = 1f
}