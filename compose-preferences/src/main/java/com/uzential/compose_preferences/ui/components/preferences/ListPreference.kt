package com.uzential.compose_preferences.ui.components.preferences

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.uzential.compose_preferences.data.Preference
import com.uzential.compose_preferences.data.compose.optimisticState
import com.uzential.compose_preferences.ui.PreferenceItem
import com.uzential.compose_preferences.ui.components.dialogs.DialogInfo
import com.uzential.compose_preferences.ui.components.dialogs.SimpleDialog

data class Choice<V>(val value: V, val displayValue: String = value.toString())

@Composable
fun <V> ListPreference(
    title: String,
    choices: List<Choice<V>>,
    preference: Preference<V>,
    dialogInfo: DialogInfo = DialogInfo(title, "Confirm", "Cancel")
) {
    var showDialog by remember { mutableStateOf(false) }

    // state
    var state by preference.optimisticState()
    // selection
    var selected by rememberSaveable(state) { // change selection when state changes (state is defaultValue at first)
        mutableStateOf(state)
    }

    val description = choices.find { it.value == state }?.displayValue

    SimpleDialog(
        dialogInfo = dialogInfo,
        open = showDialog,
        onDismiss = {
            // reset selection
            selected = state
            showDialog = false
        },
        onConfirm = {
            // save state
            state = selected
            showDialog = false
        }) {
        RadioGroup(list = choices, selected = selected, onSelect = { selected = it })
    }

    PreferenceItem(
        title = title,
        description = description,
        onClick = { showDialog = true })
}

@Composable
fun <V> RadioGroup(list: List<Choice<V>>, selected: V, onSelect: (V) -> Unit) {
    LazyColumn() {
        items(list) { choice ->
            val isSelected = selected == choice.value
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(selected = isSelected, onClick = { onSelect(choice.value) },),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = isSelected, onClick = { onSelect(choice.value) })
                Text(text = choice.displayValue)
            }
        }
    }
}

