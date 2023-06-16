package com.uzential.compose_preferences.ui.components

import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import com.uzential.compose_preferences.data.Preference
import com.uzential.compose_preferences.data.compose.optimisticState
import com.uzential.compose_preferences.data.preferences.BooleanPreference
import com.uzential.compose_preferences.ui.ComposeFunction
import com.uzential.compose_preferences.ui.PreferenceItem
import com.uzential.compose_preferences.ui.PreferencesScope

fun PreferencesScope.switchPreference(
    title: String,
    preference: Preference<Boolean>,
    description: String? = null,
    icon: ComposeFunction? = null
) {
    item {
        SwitchPreference(
            title = title,
            preference = preference,
            description = description,
            icon = icon
        )
    }
}

@Composable
fun SwitchPreference(
    title: String,
    preference: Preference<Boolean>,
    description: String? = null,
    icon: ComposeFunction? = null
) {
    var checked by preference.optimisticState()

    PreferenceItem(
        title = title,
        description = description,
        icon = icon,
        onClick = { checked = !checked },
        action = {
            Switch(checked = checked, onCheckedChange = { checked = it })
        })
}

@Preview
@Composable
private fun Preview() {
    SwitchPreference(title = "Toggle", preference = BooleanPreference("keyName"))
}