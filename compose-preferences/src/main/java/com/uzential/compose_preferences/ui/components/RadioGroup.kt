package com.uzential.compose_preferences.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

data class Choice<V>(val value: V, val displayValue: String = value.toString())

@Composable
fun <V> RadioGroup(choices: List<Choice<out V>>, selected: V, onSelect: (V) -> Unit) {
    LazyColumn {
        items(choices) { choice ->
            val isSelected = selected == choice.value

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectable(selected = isSelected, onClick = { onSelect(choice.value) }),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = isSelected, onClick = { onSelect(choice.value) })
                Text(text = choice.displayValue)
            }
        }
    }
}

@Preview
@Composable
private fun RadioGroupPreview() = Surface {
    val choices = listOf(
        Choice(1),
        Choice(2),
        Choice(null)
    )

    var state by remember {
        mutableStateOf<Int?>(2)
    }

    RadioGroup(
        choices = choices,
        selected = state,
        onSelect = { state = it }
    )
}