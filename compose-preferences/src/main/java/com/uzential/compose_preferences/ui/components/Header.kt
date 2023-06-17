package com.uzential.compose_preferences.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.uzential.compose_preferences.ui.PreferencesScope
import com.uzential.compose_preferences.ui.providers.LocalSpacing

fun PreferencesScope.header(modifier: Modifier = Modifier, title: String) {
    item {
        Header(title = title)
    }
}

@Composable
fun Header(modifier: Modifier = Modifier, title: String) {
    Box(modifier = modifier.padding(LocalSpacing.current.itemPadding)) {
        Text(title, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.SemiBold)
    }
}