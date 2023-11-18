package com.uzential.compose_preferences_demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.uzential.compose_preferences.data.preferences.BooleanPreference
import com.uzential.compose_preferences.data.preferences.compose.DarkThemePreference
import com.uzential.compose_preferences.ui.PreferenceScreen
import com.uzential.compose_preferences.ui.preferences.SwitchPreference
import com.uzential.compose_preferences.ui.providers.DataStoreProvider
import com.uzential.compose_preferences_demo.ui.theme.ComposePreferencesTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreProvider {
                ComposePreferencesTheme(
                    darkTheme = Settings.darkTheme.value ?: return@DataStoreProvider,
                    dynamicColor = Settings.dynamicColor.value ?: return@DataStoreProvider
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Screen()
                    }
                }
            }
        }
    }
}

object Settings {
    val darkTheme = DarkThemePreference()
    val dynamicColor = BooleanPreference("dynamic_color")
}

@Composable
fun Screen() {
    PreferenceScreen {
        item {
            SwitchPreference(
                title = { Text(text = "Dark theme") },
                preference = Settings.darkTheme
            )

            SwitchPreference(
                title = { Text(text = "Dynamic color") },
                preference = Settings.dynamicColor
            )
        }
    }
}