package com.uzential.compose_preferences.ui.preferences

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uzential.compose_preferences.data.Preference
import com.uzential.compose_preferences.data.compose.state
import com.uzential.compose_preferences.data.preferences.BooleanPreference
import com.uzential.compose_preferences.ui.PreferenceItem
import com.uzential.compose_preferences.ui.PreferenceScope
import com.uzential.compose_preferences.ui.providers.DataStoreProvider
import com.uzential.compose_preferences.ui.providers.LocalDataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


fun PreferenceScope.switchPreference(
    title: String,
    preference: Preference<Boolean>,
    description: String? = null,
    icon: @Composable () -> Unit = {},
) {
    item {
        SwitchPreference(
            title = { Text(title) },
            preference = preference,
            description = { description?.let { Text(text = it) } },
            icon = icon
        )
    }
}

@Composable
fun SwitchPreference(
    title: @Composable () -> Unit,
    preference: Preference<Boolean>,
    modifier: Modifier = Modifier,
    description: @Composable () -> Unit = {},
    icon: @Composable () -> Unit = {},
) {
    var checked by preference.state()

    SwitchPreference(
        title = title,
        checked = checked ?: return,
        onCheckedChange = { checked = it },
        modifier = modifier,
        description = description,
        icon = icon
    )
}

@Composable
fun SwitchPreference(
    title: @Composable () -> Unit,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    description: @Composable () -> Unit = {},
    icon: @Composable () -> Unit = {},
) {
    PreferenceItem(
        modifier = modifier,
        title = title,
        description = description,
        icon = icon,
        onClick = { onCheckedChange(!checked) }
    ) {
        Column {
            Switch(checked = checked, onCheckedChange = onCheckedChange)
            Text(text = "Checked $checked")
        }
    }
}


@Preview()
@Composable
private fun SwitchPreferencePreview() = DataStoreProvider {
    val preference = BooleanPreference("abc", true)
    val datastore = LocalDataStore.current

    var checked by preference.state()

    val test = runBlocking {
        preference.flow(datastore).first()
    }
    Surface {
        Column {
            SwitchPreference(
                title = {
                    Text("Title $checked")
                },
                preference = preference,
            )
            val setValue = preference.setValue()
            Button(onClick = {
                checked = true
            }) {
                Text("Button $test")
            }
        }
    }
}
