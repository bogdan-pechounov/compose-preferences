package com.uzential.compose_preferences.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.uzential.compose_preferences.ui.providers.LocalSpacing

typealias ComposeFunction = @Composable () -> Unit

fun PreferencesScope.preferenceItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    onClick: () -> Unit = {},
    icon: ComposeFunction? = null,
    action: ComposeFunction = {},
) {
    item {
        PreferenceItem(
            title = title,
            description = description,
            onClick = onClick,
            icon = icon,
            action = action
        )
    }
}

@Composable
fun PreferenceItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String? = null,
    onClick: () -> Unit = {},
    icon: ComposeFunction? = null,
    action: ComposeFunction? = null,
) {
    Row(modifier = modifier
        .fillMaxWidth()
        .heightIn(min = LocalSpacing.current.itemMinHeight)
        .clickable { onClick() }
        .padding(LocalSpacing.current.itemPadding),
        verticalAlignment = Alignment.CenterVertically)
    {
        IconContainer(icon = icon)
        Details(modifier = Modifier.weight(1f), title = title, description = description)
        ActionContainer(action = action)
    }
}

@Composable
fun ActionContainer(modifier: Modifier = Modifier, action: ComposeFunction?){
    if(action == null) return
    Box(
        modifier = modifier.padding(LocalSpacing.current.actionPadding),
    ) {
        action()
    }
}

@Composable
fun IconContainer(modifier: Modifier = Modifier, icon: ComposeFunction?) {
    if (icon == null) return
    Box(
        modifier = modifier.padding(LocalSpacing.current.iconPadding),
    ) {
        icon()
    }
}

@Composable
fun Details(modifier: Modifier = Modifier, title: String, description: String?) {
    Column(modifier = modifier) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        description?.let { Text(it, style = MaterialTheme.typography.bodyMedium) }
    }
}

@Preview
@Composable
private fun Preview() {
    Column() {
        PreferenceItem(title = "Item",
            icon = { Icon(imageVector = Icons.Outlined.Build, contentDescription = null) },
            action = { Switch(checked = true, onCheckedChange = {}) })
        PreferenceItem(title = "Feedback",
            description = "Description",
            icon = { Icon(imageVector = Icons.Filled.Send, contentDescription = null) },
            action = {
                Button(onClick = {}) {
                    Text(text = "Send")
                }
            })
        PreferenceItem(title = "Delete",
            description = LoremIpsum(words = 20).values.joinToString(),
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier
                )
            },
            action = {
                Button(onClick = {}) {
                    Text(text = "Delete")
                }
            })
    }
}