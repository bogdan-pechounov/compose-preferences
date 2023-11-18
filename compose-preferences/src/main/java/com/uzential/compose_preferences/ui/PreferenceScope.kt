package com.uzential.compose_preferences.ui

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.lazy.LazyListScope


/**
 * LazyListScope with additional methods
 */
interface PreferenceScope : LazyListScope {
    val context: Context

    fun stringResource(@StringRes id: Int) = context.getString(id)
}

internal class PreferenceScopeImpl(
    lazyListScope: LazyListScope,
    override val context: Context,
) : PreferenceScope, LazyListScope by lazyListScope