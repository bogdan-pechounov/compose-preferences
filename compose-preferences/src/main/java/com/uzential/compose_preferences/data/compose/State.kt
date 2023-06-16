package com.uzential.compose_preferences.data.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import com.uzential.compose_preferences.data.Preference
import com.uzential.compose_preferences.ui.providers.LocalDataStore

/**
 * Avoid having to specify an initial value
 */
@Composable
fun <V> Preference<V>.stateOrDefault() =
    flowOrDefault(LocalDataStore.current, LocalContext.current).collectAsState(initial = defaultValue()) // TODO with lifecycle

@Composable
fun <V> Preference<V>.state() = flow(LocalDataStore.current).collectAsState(initial = null)

@Composable
fun <V> Preference<V>.defaultValue() = defaultValue(LocalContext.current)

