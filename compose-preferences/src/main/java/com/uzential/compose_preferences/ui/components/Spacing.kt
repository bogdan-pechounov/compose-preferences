package com.uzential.compose_preferences.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import com.uzential.compose_preferences.ui.providers.u

@Composable
fun Spacing(
    size: Dp = 4.u
){
    Spacer(modifier = Modifier.size(size))
}