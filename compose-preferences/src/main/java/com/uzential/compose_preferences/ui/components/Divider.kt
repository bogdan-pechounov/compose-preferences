package com.uzential.compose_preferences.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.ui.Modifier
import com.uzential.compose_preferences.ui.PreferencesScope
import com.uzential.compose_preferences.ui.providers.LocalSpacing

fun PreferencesScope.divider(modifier: Modifier = Modifier) {
    item {
        Divider(
            modifier = modifier
                .padding(LocalSpacing.current.dividerPadding)
        )
    }
}