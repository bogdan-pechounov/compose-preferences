package com.uzential.compose_preferences.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.uzential.compose_preferences.ui.PreferenceScope
import com.uzential.compose_preferences.ui.providers.LocalPreferenceSpacing
import com.uzential.compose_preferences.ui.providers.u

fun PreferenceScope.header(title: String) {
    item {
        Header(title = title)
    }
}

@Composable
fun Header(
    title: String,
    padding: PaddingValues = PaddingValues(top = 2.u)
) {
    Box(modifier = Modifier.padding(LocalPreferenceSpacing.current.itemPadding)) {
        Text(
            title,
            modifier = Modifier.padding(padding),
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Preview
@Composable
private fun HeaderPreview() = Surface {
    Header(title = "Hi")
}