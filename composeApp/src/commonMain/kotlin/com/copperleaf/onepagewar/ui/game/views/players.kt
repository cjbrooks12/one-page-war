package com.copperleaf.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.copperleaf.onepagewar.resources.MR
import com.copperleaf.onepagewar.vm.game.GameContract
import com.copperleaf.onepagewar.vm.game.models.Player

@Composable
internal fun PlayerTabRow(
    state: GameContract.State,
    focusedPlayerIndex: Int,

    focusedPlayerChanged: (Int) -> Unit,
) {
    when (state.players.size) {
        0 -> {
            // game not set up correctly
        }

        1 -> {
            // single-player, don't show tabs
        }

        2 -> {
            // two-player, show fixed-size tabs
            TabRow(
                selectedTabIndex = focusedPlayerIndex,
                tabs = {
                    PlayerTabs(
                        state = state,
                        focusedPlayerIndex = focusedPlayerIndex,
                        focusedPlayerChanged = focusedPlayerChanged,
                    )
                }
            )
        }

        else -> {
            ScrollableTabRow(
                selectedTabIndex = focusedPlayerIndex,
                tabs = {
                    PlayerTabs(
                        state = state,
                        focusedPlayerIndex = focusedPlayerIndex,
                        focusedPlayerChanged = focusedPlayerChanged,
                    )
                }
            )
        }
    }
}

@Composable
internal fun PlayerTabs(
    state: GameContract.State,
    focusedPlayerIndex: Int,

    focusedPlayerChanged: (Int) -> Unit,
) {
    state.players.forEachIndexed { index, player ->
        Tab(
            selected = focusedPlayerIndex == index,
            onClick = { focusedPlayerChanged(index) },
            text = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    if (player.bot) {
                        Icon(Icons.Default.SmartToy, "Bot")
                    } else {
                        Icon(Icons.Default.Person, "Human")
                    }
                    Text(player.name)
                }
            },
        )
    }
}

@Composable
internal fun PlayerDetails(
    state: GameContract.State,
    focusedPlayer: Player,

    postInput: (GameContract.Inputs) -> Unit,
) {
    var playerDetailsDialogIsOpen by remember { mutableStateOf(false) }

    when (state.players.size) {
        0 -> {
            // game not set up correctly
        }

        1 -> {
            // single-player, don't show player details
        }

        else -> {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(focusedPlayer.name, modifier = Modifier.weight(1f))

                IconButton(
                    onClick = { postInput(GameContract.Inputs.RemovePlayer(focusedPlayer.name)) },
                ) {
                    Icon(Icons.Default.RemoveCircle, "Remove Player")
                }

                IconButton(
                    onClick = { playerDetailsDialogIsOpen = true },
                ) {
                    Icon(Icons.Default.Settings, "Edit Player Details")
                }
            }
        }
    }

    if (playerDetailsDialogIsOpen) {
        PlayerSettingsPopup(
            state = state,
            initialName = focusedPlayer.name,
            initialIsBot = focusedPlayer.bot,
            onDismissRequest = {
                playerDetailsDialogIsOpen = false
            },
            savePlayerDetails = { name, bot ->
                playerDetailsDialogIsOpen = false
                postInput(GameContract.Inputs.UpdatePlayerDetails(focusedPlayer.name, name, bot))
            },
            newPlayerPopup = false,
        )
    }
}

@Composable
internal fun PlayerSettingsPopup(
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
