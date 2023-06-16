package com.uzential.compose_preferences.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import com.uzential.compose_preferences.data.Preference

/**
 * Base class for preferences
 * @param key
 * @param defaultValue default value using a context to access resources if necessary
 */
open class BasePreference<V>(
    override val key: Preferences.Key<V>, override val defaultValue: Context.() -> V
) : Preference<V>