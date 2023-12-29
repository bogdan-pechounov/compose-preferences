package com.uzential.compose_preferences.ui.preferences

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.uzential.common_strings.CommonStrings
import com.uzential.compose_preferences.data.Preference
import com.uzential.compose_preferences.data.compose.state
import com.uzential.compose_preferences.ui.PreferenceItem
import com.uzential.compose_preferences.ui.components.Choice


@Composable
fun <V> DropdownPreference(
    title: String,
    choices: List<Choice<out V?>>,
    preference: Preference<V>,
    description: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    var state by preference.state()

    DropdownPreference(
        title = { Text(text = title) },
        choices = choices,
        selected = state,
        onSelect = { state = it },
        description = description,
        icon = icon
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <V> DropdownPreference(
    title: @Composable () -> Unit,
    choices: List<Choice<out V>>,
    selected: V,
    onSelect: (V) -> Unit,
    modifier: Modifier = Modifier,
    description: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
) {
    var expanded by remember {
        mutableStateOf(false)
    }

    val selectedChoice = choices.find { it.value == selected } ?: return

    PreferenceItem(
        modifier = modifier,
        title = title,
        description = description,
        icon = icon,
        onClick = {
            expanded = !expanded
        },
        action = {
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = selectedChoice.displayValue,
                        modifier = Modifier.padding(4.dp)
                    )
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                }


                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    choices.forEach { choice ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    modifier = Modifier.weight(1f),
                                    text = choice.displayValue
                                )
                            },
                            onClick = {
                                onSelect(choice.value)
                                expanded = false
                            }
                        )
                    }
                }
            }
        }
    )
}


@Composable
fun Demo_DropDownMenu() {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            //.fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("L") },
                onClick = { Toast.makeText(context, "L", Toast.LENGTH_SHORT).show() }
            )
            DropdownMenuItem(
                text = { Text("Save a very super long word") },
                onClick = {
                    Toast.makeText(
                        context,
                        "Save a very super long word",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }
}

@Preview
@Composable
fun DropdownPreferencePreview() = Surface {
    val choices = listOf(
        Choice(false, "L"),
        Choice(true, "Dark"),
        Choice(null, "Device")
    )

    var selected: Boolean? by remember {
        mutableStateOf(null)
    }

    DropdownPreference(
        title = { Text(text = "Theme") },
        icon = {
            Icon(imageVector = Icons.Default.ThumbUp, contentDescription = null)
        },
        choices = choices,
        selected = selected,
        onSelect = { selected = it }
    )
}