package com.uzential.composepreferences.data.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.uzential.composepreferences.data.Preference
import com.uzential.composepreferences.ui.providers.LocalDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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

