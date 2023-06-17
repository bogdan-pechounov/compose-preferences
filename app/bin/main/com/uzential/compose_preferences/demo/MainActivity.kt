package com.uzential.compose_preferences.demo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.uzential.compose_preferences.data.preferences.IntPreference
import com.uzential.compose_preferences.demo.ui.theme.ComposePreferencesTheme
import com.uzential.compose_preferences.ui.PreferenceScreen
import com.uzential.compose_preferences.ui.components.preferences.Choice
import com.uzential.compose_preferences.ui.components.preferences.ListPreference
import com.uzential.compose_preferences.ui.providers.DataStoreProvider

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreProvider {
                ComposePreferencesTheme {
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


val choice = IntPreference("choice", defaultValue = 1)
val choices = listOf(Choice(1, "Test"), Choice(2), Choice(3), Choice(4, "Animal"), Choice(5))

@Composable
fun Test() {
    PreferenceScreen {
        item {
            ListPreference(title = "Title", choices = choices, preference = choice)
        }
    }
}