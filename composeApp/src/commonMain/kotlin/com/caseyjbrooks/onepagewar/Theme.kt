package com.caseyjbrooks.onepagewar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val AppLightColorScheme = lightColorScheme(
    // M3 light Color parameters
)
private val AppDarkColorScheme = darkColorScheme(
    // M3 dark Color parameters
)

@Composable
internal fun AppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colorScheme = if (useDarkTheme) AppDarkColorScheme else AppLightColorScheme,
        content = { Surface(content = content) }
    )
}
