package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.ListItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.caseyjbrooks.onepagewar.resources.MR
import com.caseyjbrooks.onepagewar.themes.GameThemes


@Composable
internal fun ThemeSelctor(
    currentThemeId: String,
    setThemeId: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        for (theme in GameThemes.allAvailableThemes) {
            val themeIsSelected = theme.id == currentThemeId
            ListItem(
                modifier = Modifier
                    .width(280.dp)
                    .toggleable(
                        enabled = theme.enabled,
                        value = themeIsSelected,
                        onValueChange = { setThemeId(theme.id) },
                    ),
                leadingContent = {
                    RadioButton(
                        enabled = theme.enabled,
                        selected = themeIsSelected,
                        onClick = null
                    )
                },
                headlineContent = {
                    if (theme.enabled) {
                        Text(theme.name())
                    } else {
                        Text("${theme.name()} (${MR.strings.coming_soon()})")
                    }
                }
            )
        }
    }
}
