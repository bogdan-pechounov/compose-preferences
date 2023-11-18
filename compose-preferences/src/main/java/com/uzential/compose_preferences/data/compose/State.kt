package com.uzential.compose_preferences.data.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalInspectionMode
import com.uzential.compose_preferences.data.Preference

val inPreviewMode
    @Composable
    get() = LocalInspectionMode.current

@Composable
fun <V> Preference<V>.state(): MutableState<V?> {
    // for previews
    if(inPreviewMode){
        val defaultValue = previewDefaultValue
        return remember {
            mutableStateOf(defaultValue)
        }
    }

    val currentValue = value
    val setValue = setValue()

    return object : MutableState<V?> {
        override var value: V?
            get() = currentValue
            set(value) = setValue(value)

        override fun component1(): V? {
            return value
        }

        override fun component2(): (V?) -> Unit = {
            value = it
        }
    }
}
