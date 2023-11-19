package com.uzential.compose_preferences.ui.preferences

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.uzential.compose_preferences.R
import com.uzential.compose_preferences.data.Preference
import com.uzential.compose_preferences.data.compose.state
import com.uzential.compose_preferences.data.preferences.compose.DarkThemePreference
import com.uzential.compose_preferences.ui.PreferenceItem
import com.uzential.compose_preferences.ui.components.Choice
import com.uzential.compose_preferences.ui.components.DialogInfo
import com.uzential.compose_preferences.ui.components.RadioGroup
import com.uzential.compose_preferences.ui.components.SimpleDialog
import com.uzential.compose_preferences.ui.providers.DataStoreProvider


@Composable
fun <V> ListPreference(
    title: String,
    preference: Preference<V>,
    choices: List<Choice<out V?>>,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    dialogInfo: DialogInfo = DialogInfo(
        title,
        stringResource(R.string.confirm),
        stringResource(R.string.cancel)
    ),
) {
    var state by preference.state()

    ListPreference(
        title = title,
        choices = choices,
        selected = state,
        onSelect = { state = it },
        modifier = modifier,
        icon = icon,
        dialogInfo = dialogInfo
    )
}

@Composable
fun <V> ListPreference(
    title: String,
    choices: List<Choice<out V>>,
    selected: V,
    onSelect: (V) -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    dialogInfo: DialogInfo = DialogInfo(
        title,
        stringResource(R.string.confirm),
        stringResource(R.string.cancel)
    ),
) {
    // selection
    var tempSelected by remember(selected) {
        mutableStateOf(selected)
    }

    // display
    var showDialog by remember { mutableStateOf(false) }
    val description = choices.find { it.value == selected }?.displayValue // TODO inefficient?

    SimpleDialog(
        dialogInfo = dialogInfo,
        open = showDialog,
        onDismiss = {
            // reset selection
            tempSelected = selected
            showDialog = false
        },
        onConfirm = {
            // save state
            onSelect(tempSelected)
            showDialog = false
        }) {
        RadioGroup(choices = choices, selected = tempSelected, onSelect = { tempSelected = it })
    }

    PreferenceItem(
        modifier = modifier,
        title = title,
        icon = icon,
        description = description,
        onClick = { showDialog = true }
    )
}

@Preview
@Composable
private fun ListPreferencePreview() = DataStoreProvider {
    val choices = listOf(
        Choice(true, "Dark Theme"),
        Choice(false, "Light Theme"),
        Choice(null, "System")
    )

    val darkThemePreference = DarkThemePreference().toActual()

    val state = darkThemePreference.state()

    Surface {
        Column {
            ListPreference(
                title = "Theme",
                choices = choices,
                selected = state.value,
                onSelect = { state.value = it }
            )
            ListPreference(
                title = "Theme",
                choices = choices,
                selected = state.value,
                onSelect = { state.value = it }
            )
        }
    }
}


