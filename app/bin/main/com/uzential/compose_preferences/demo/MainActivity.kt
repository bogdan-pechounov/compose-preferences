package com.uzential.compose_preferences.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.uzential.compose_preferences.data.preferences.common.DarkThemePreference
import com.uzential.compose_preferences.data.compose.state
import com.uzential.compose_preferences.data.compose.stateOrDefault
import com.uzential.compose_preferences.data.preferences.BooleanPreference
import com.uzential.compose_preferences.demo.ui.theme.ComposePreferencesTheme
import com.uzential.compose_preferences.ui.PreferenceScreen
import com.uzential.compose_preferences.ui.components.SwitchPreference
import com.uzential.compose_preferences.ui.components.header
import com.uzential.compose_preferences.ui.components.switchPreference
import com.uzential.compose_preferences.ui.providers.DataStoreProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreProvider {
                ComposePreferencesTheme(darkTheme = DARK_THEME.stateOrDefault().value) {
                    // A surface container using the 'background' color from the theme
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background,
                    ) {
                        Test()
                    }
                }
            }
        }
    }
}

val SHOW = BooleanPreference("test2", defaultValue = false)
val DARK_THEME = DarkThemePreference()

@Composable
fun Example() {
    val show by SHOW.stateOrDefault()
    Text(text = SHOW.state().value.toString())
    if (show) Text(text = "Shown")
    else Text(text = "Not shown")
}

@Composable
fun Test() {
    PreferenceScreen {
        header(title ="General")
        switchPreference(title = "Show", description = "Description", icon = {
            Icon(imageVector = Icons.Default.Home, contentDescription = null)
        }, preference = SHOW)

        item {
            SwitchPreference(title = "Dark theme", preference = DARK_THEME, icon = {
                Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            })
        }

        item {
            Divider()
        }

        item {
            Example()
        }
        item {
            Text(text = "${DARK_THEME.state().value} ${DARK_THEME.stateOrDefault().value}")
        }
    }
}