package com.uzential.compose_preferences.ui

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.uzential.compose_preferences.R
import com.uzential.compose_preferences.data.preferences.compose.DarkThemePreference
import com.uzential.compose_preferences.ui.preferences.switchPreference

/**
 * LazyColumn with an extended scope
 */
@Composable
fun PreferenceScreen(
    modifier: Modifier = Modifier,
    content: PreferenceScope.() -> Unit,
) {
    val context = LocalContext.current
    LazyColumn(modifier = modifier) {
        content(PreferenceScopeImpl(this, context))
    }
}


@Preview
@Composable
private fun PreferenceScreenPreview() = Surface {
    PreferenceScreen {
        preferenceItem(
            title = {
                Text(text = stringResource(R.string.title))
            },
            description = {
                Text(text = "Description", style = MaterialTheme.typography.bodySmall)
            },
            icon = {
                Icon(imageVector = Icons.Outlined.Settings, contentDescription = null)
            }
        )

        switchPreference(
            title = "Dark theme",
            preference = DarkThemePreference(),
            icon = {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
            }
        )
    }
}