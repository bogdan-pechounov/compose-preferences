package com.uzential.composepreferences.ui.components

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.uzential.composepreferences.data.BooleanPreference
import com.uzential.composepreferences.data.compose.optimisticState
import com.uzential.composepreferences.ui.PreferenceItem


@Composable
fun SwitchPreference(title: String, preference: BooleanPreference) {
    var checked by preference.optimisticState()

    PreferenceItem(title = title, action = {
        Switch(checked = checked, onCheckedChange = { checked = it })
    }, onClick = { checked = !checked })
}