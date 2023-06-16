package com.uzential.composepreferences.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

fun PreferencesScope.preferenceItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    action: @Composable () -> Unit = {},
    onClick: () -> Unit = {}
) {
    item {
        PreferenceItem(
            modifier = modifier,
            title = title,
            description = description,
            action = action,
            onClick = onClick
        )
    }
}

@Composable
fun PreferenceItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    action: @Composable () -> Unit = {},
    onClick: () -> Unit = {}
) {

    Row(modifier = modifier
        .fillMaxWidth()
        .heightIn(min = 48.dp)
        .clickable { onClick() }) {
        Details(modifier = Modifier.weight(1f), title = title, description = description)
        action()
    }
}

@Composable
fun Details(modifier: Modifier = Modifier, title: String, description: String?) {
    Column(modifier = modifier) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        description?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
    }
}