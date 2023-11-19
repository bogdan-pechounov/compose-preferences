package com.uzential.compose_preferences_demo.ui.examples

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uzential.compose_preferences.data.preferences.compose.DarkThemePreference
import com.uzential.compose_preferences.ui.PreferenceItem
import com.uzential.compose_preferences.ui.PreferenceScreen
import com.uzential.compose_preferences.ui.components.Header
import com.uzential.compose_preferences.ui.components.Spacing
import com.uzential.compose_preferences.ui.preferences.SwitchPreference
import com.uzential.compose_preferences_demo.ui.theme.ComposePreferencesTheme


@Composable
fun PreferenceScreenExample() {
    val darkThemePreference = DarkThemePreference()

    PreferenceScreen {
        item {
            Header(title = "General")
            SwitchPreference(
                title = "Dark theme", preference = darkThemePreference
            )
            SwitchPreference(
                title = { Text(text = "Dark theme") },
                description = { Text(text = "Change theme") },
                checked = darkThemePreference.value ?: return@item,
                onCheckedChange = darkThemePreference.setValue()
            )
            Divider()
        }


        item {
            Header(title = "Contact")
            PreferenceItem(
                title = "Send feedback",
                icon = {
                    Icon(
                        imageVector = Icons.Default.Send, contentDescription = null
                    )
                },
                onClick = {}
            )
        }

        item {
            Spacing()
            Header(title = "Privacy")
            PreferenceItem(
                title = { Text("Privacy policy") },
                onClick = {},
                action = {
                    Icon(imageVector = Icons.Default.OpenInNew, contentDescription = null)
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES or Configuration.UI_MODE_TYPE_NORMAL
)
@Preview
@Composable
private fun PreferenceScreenExamplePreview() = ComposePreferencesTheme {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Settings") },
            navigationIcon = {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }
        )
    }) {
        Box(modifier = Modifier.padding(it)) {
            PreferenceScreenExample()
        }
    }
}