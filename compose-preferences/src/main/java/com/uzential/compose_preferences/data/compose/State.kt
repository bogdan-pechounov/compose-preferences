package com.uzential.compose_preferences.data.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uzential.compose_preferences.data.Preference
import com.uzential.compose_preferences.ui.providers.LocalDataStore

/**
 * Avoid having to specify an initial value. The saved value might be different from the initial value, causing some flickering.
 */
@Composable
fun <V> Preference<V>.stateOrDefault() =
    flowOrDefault(LocalDataStore.current, LocalContext.current)
        .collectAsStateWithLifecycle(initialValue = defaultValue())

/**
 * Avoid flickering if the saved value is different from the default value.
 */
@Composable
fun <V> Preference<V>.waitForValue() =
    flowOrDefault(LocalDataStore.current, LocalContext.current)
        .collectAsStateWithLifecycle(initialValue = null).value


@Composable
fun <V> Preference<V>.state() =
    flow(LocalDataStore.current).collectAsStateWithLifecycle(initialValue = null)

@Composable
fun <V> Preference<V>.defaultValue() = defaultValueFromContext(LocalContext.current)

