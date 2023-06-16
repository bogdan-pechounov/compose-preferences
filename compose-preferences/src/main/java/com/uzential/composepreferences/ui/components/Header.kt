package com.uzential.composepreferences.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.uzential.composepreferences.ui.PreferencesScope
import com.uzential.composepreferences.ui.providers.LocalSpacing

fun PreferencesScope.header(title: String) {
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