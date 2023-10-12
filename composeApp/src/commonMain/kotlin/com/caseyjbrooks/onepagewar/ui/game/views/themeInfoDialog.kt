package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.caseyjbrooks.onepagewar.vm.game.models.Theme

@Composable
internal fun ThemeInfoDialog(
    selectedTheme: Theme,

    onDismissRequest: () -> Unit,
) {
    AlertDialog(
        title = {
            Text("Theme Info")
        },
        onDismissRequest = onDismissRequest,
        text = {
            Column {
                Text("Hand Values")

                selectedTheme.handValues.forEach {
                    Text(it())
                }
            }
        },
        confirmButton = {
            OutlinedButton(
                onClick = { onDismissRequest() },
            ) {
                Text("Close")
            }
        },
    )
}
