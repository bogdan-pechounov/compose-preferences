package com.uzential.composepreferences.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.uzential.composepreferences.data.BooleanPreference
import com.uzential.composepreferences.data.compose.DarkThemePreference
import com.uzential.composepreferences.data.compose.state
import com.uzential.composepreferences.data.compose.stateOrDefault
import com.uzential.composepreferences.demo.ui.theme.ComposePreferencesTheme
import com.uzential.composepreferences.ui.PreferenceScreen
import com.uzential.composepreferences.ui.components.SwitchPreference
import com.uzential.composepreferences.ui.providers.DataStoreProvider

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

val SHOW = BooleanPreference("show", defaultValue = false)
val DARK_THEME = DarkThemePreference()

@Composable
fun Example() {
    val show by SHOW.stateOrDefault()

    if (show) Text(text = "Shown")
    else Text(text = "Not shown")
}

@Composable
fun Test() {
    PreferenceScreen {
        item {
            SwitchPreference(title = "SHOW", preference = SHOW)
        }

        item {
            Example()
        }
        item {
            Text(text = "${DARK_THEME.state().value} ${DARK_THEME.stateOrDefault().value}")
        }

        item {
            SwitchPreference(title = "Dark theme", preference = DARK_THEME)
        }
    }
}