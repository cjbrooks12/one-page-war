package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.caseyjbrooks.onepagewar.resources.MR
import com.caseyjbrooks.onepagewar.vm.game.GameContract

@Composable
internal fun PlayerSettingsDialog(
    state: GameContract.State,
    initialName: String,
    initialIsBot: Boolean,
    newPlayerPopup: Boolean,

    onDismissRequest: () -> Unit,
    savePlayerDetails: (String, Boolean) -> Unit,
) {
    var playerNameText by remember(initialName) { mutableStateOf(TextFieldValue(initialName)) }
    val newPlayerNameIsValid = if(newPlayerPopup) {
        // new players can reuse names
        playerNameText.text.isNotBlank() &&
                playerNameText.text !in state.players.map { it.name }
    } else {
        // existing players cannot reuse names, unless it's their own (weird validation logic)
        playerNameText.text.isNotBlank() &&
                (playerNameText.text == initialName || playerNameText.text !in state.players.map { it.name })
    }

    var playerIsBot by remember(initialIsBot) { mutableStateOf(initialIsBot) }

    AlertDialog(
        title = {
            if (newPlayerPopup) {
                Text("New Player")
            } else {
                Text("Update Player")
            }
        },
        onDismissRequest = onDismissRequest,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                ListItem(
                    modifier = Modifier
                        .width(280.dp)
                        .toggleable(
                            value = playerIsBot,
                            onValueChange = { playerIsBot = false },
                        ),
                    leadingContent = {
                        RadioButton(
                            selected = !playerIsBot,
                            onClick = null
                        )
                    },
                    headlineContent = {
                        Text(MR.strings.human())
                    }
                )
                ListItem(
                    modifier = Modifier
                        .width(280.dp)
                        .toggleable(
                            value = playerIsBot,
                            onValueChange = { playerIsBot = true },
                        ),
                    leadingContent = {
                        RadioButton(
                            selected = playerIsBot,
                            onClick = null
                        )
                    },
                    headlineContent = {
                        Text(MR.strings.bot())
                    }
                )

                TextField(
                    value = playerNameText,
                    onValueChange = { playerNameText = it },
                    label = { Text("Player Name") },
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (newPlayerNameIsValid) {
                        savePlayerDetails(
                            playerNameText.text,
                            playerIsBot,
                        )
                    }
                },
                enabled = newPlayerNameIsValid
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDismissRequest() },
            ) {
                Text("Close")
            }
        },
    )
}
