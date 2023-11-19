package com.uzential.compose_preferences.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.datasource.LoremIpsum
import com.uzential.compose_preferences.ui.providers.LocalPreferenceSpacing

fun PreferenceScope.preferenceItem(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
    action: @Composable (() -> Unit)? = null,
) {
    item {
        PreferenceItem(
            title = { Text(text = title) },
            modifier = modifier,
            description = { description?.let { Text(text = it) } },
            icon = icon,
            onClick = onClick,
            action = action
        )
    }
}


fun PreferenceScope.preferenceItem(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    description: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
    action: @Composable (() -> Unit)? = null,
) {
    item {
        PreferenceItem(
            title = title,
            modifier = modifier,
            description = description,
            icon = icon,
            onClick = onClick,
            action = action
        )
    }
}

@Composable
fun PreferenceItem(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
    action: @Composable (() -> Unit)? = null,
) {
    PreferenceItem(
        title = { Text(text = title) },
        modifier = modifier,
        description = { description?.let { Text(text = it) } },
        icon = icon,
        onClick = onClick,
        action = action
    )
}

@Composable
fun PreferenceItem(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    description: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
    action: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = LocalPreferenceSpacing.current.itemMinHeight)
            .clickable { onClick() }
            .padding(LocalPreferenceSpacing.current.itemPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            IconContainer(icon = icon)
        }
        Details(
            title = title,
            description = description
        )
        if (action != null) {
            ActionContainer(action = action)
        }
    }
}

@Composable
private fun IconContainer(
    icon: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.padding(LocalPreferenceSpacing.current.iconPadding),
    ) {
        icon()
    }
}

@Composable
private fun ActionContainer(
    action: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.padding(LocalPreferenceSpacing.current.actionPadding),
    ) {
        action()
    }
}

@Composable
private fun RowScope.Details(
    title: @Composable () -> Unit,
    description: @Composable (() -> Unit)?,
) {
    Column(modifier = Modifier.weight(1f)) {
        CompositionLocalProvider(LocalTextStyle.provides(MaterialTheme.typography.titleMedium)) {
            title()
        }
        if (description != null) {
            CompositionLocalProvider(LocalTextStyle.provides(MaterialTheme.typography.bodyMedium)) {
                description()
            }
        }
    }
}

@Preview
@Composable
private fun PreferenceItemPreview() = Surface {
    Column {
        var checked by remember {
            mutableStateOf(true)
        }
        PreferenceItem(
            title = "Item",
            icon = { Icon(imageVector = Icons.Outlined.Build, contentDescription = null) }
        ) { Switch(checked = checked, onCheckedChange = { checked = it }) }

        PreferenceItem(
            title = "Feedback",
            description = "Description",
            icon = { Icon(imageVector = Icons.Filled.Send, contentDescription = null) }
        ) {
            Button(onClick = {}) {
                Text(text = "Send")
            }
        }

        PreferenceItem(
            title = "Delete",
            description = LoremIpsum(words = 20).values.joinToString(),
            icon = {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = null,
                    modifier = Modifier
                )
            }
        ) {
            Button(onClick = {}) {
                Text(text = "Delete")
            }
        }
    }
}