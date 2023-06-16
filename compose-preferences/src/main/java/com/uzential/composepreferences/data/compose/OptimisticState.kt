package com.uzential.composepreferences.data.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.uzential.composepreferences.data.Preference
import com.uzential.composepreferences.ui.providers.LocalDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

/**
 * Optimistic update of preference
 *
 * Notes: I ran some tests with a Switch by printing the current time when onCheckedChange is called and when the function is recomposed. Updating a boolean preference with .state(dataStore) took about 40 ms, while changing a boolean immediately with mutableStateOf took about 10ms.
 */
@Composable
fun <V> Preference<V>.optimisticState(): MutableState<V> {
    val context = LocalContext.current
    val optimisticState = remember { mutableStateOf(defaultValue(context)) }

    // initialize with saved value
    val dataStore = LocalDataStore.current
    LaunchedEffect(key1 = null, block = {
        val savedState = flow(dataStore).first()
        if (savedState != null) {
            optimisticState.value = savedState
        }
    })

    // override setter
    val scope = rememberCoroutineScope()
    return object : MutableState<V> by optimisticState {
        override var value: V
            get() = optimisticState.value
            set(value) {
                // change state immediately and then save the new value
                optimisticState.value = value
                scope.launch {
                    edit(dataStore) { value }
                }
            }
    }
}