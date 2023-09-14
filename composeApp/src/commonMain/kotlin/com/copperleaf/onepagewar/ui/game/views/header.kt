package com.copperleaf.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.copperleaf.onepagewar.resources.MR
import com.copperleaf.onepagewar.themes.GameThemes
import com.copperleaf.onepagewar.vm.game.GameContract
import com.copperleaf.onepagewar.vm.game.models.Theme

@Composable
internal fun Header(
    selectedTheme: Theme,
    state: GameContract.State,
    postInput: (GameContract.Inputs) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        var isGameIdPopupVisible by remember { mutableStateOf(false) }
        var newPlayerPopupVisible by remember { mutableStateOf(false) }
        ListItem(
            leadingContent = {
                IconButton(
                    onClick = { isGameIdPopupVisible = !isGameIdPopupVisible },
                    enabled = !state.diceIsRolling,
                ) {
                    Icon(Icons.Default.Info, "Game ID")
                }
            },
            headlineContent = {
                Row {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { postInput(GameContract.Inputs.NewGame) },
                    ) {
                        Text("New Game")
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { newPlayerPopupVisible = true },
                    ) {
                        Text("Add Player")
                    }
                }
            }
        )

        if (isGameIdPopupVisible) {
            GameSettingsPopup(
                selectedTheme = selectedTheme,
                currentGameId = state.gameId,
                onDismissRequest = { isGameIdPopupVisible = false },
                postInput = {
                    isGameIdPopupVisible = false
                    postInput(it)
                }
            )
        }

        if (newPlayerPopupVisible) {
            PlayerSettingsPopup(
                state = state,
                initialName = "Player ${state.players.size + 1}",
                initialIsBot = false,
                onDismissRequest = {
                    newPlayerPopupVisible = false
                },
                savePlayerDetails = { name, bot ->
                    newPlayerPopupVisible = false
                    postInput(GameContract.Inputs.AddPlayer(name, bot))
                },
                newPlayerPopup = true,
            )
        }
    }
}

@Composable
private fun GameSettingsPopup(
    selectedTheme: Theme,

    currentGameId: Int,
    onDismissRequest: () -> Unit,
    postInput: (GameContract.Inputs) -> Unit,
) {
    var themeId by remember(selectedTheme) { mutableStateOf(selectedTheme.id) }

    var gameIdText by remember(currentGameId) { mutableStateOf(TextFieldValue("$currentGameId")) }
    val gameIdNumber = gameIdText.text.toIntOrNull()
    val newGameIdIsValid = gameIdNumber != null && gameIdNumber in 1000..10_000

    AlertDialog(
        title = {
            Text("Game Settings")
        },
        onDismissRequest = onDismissRequest,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                for (theme in GameThemes.allAvailableThemes) {
                    val themeIsSelected = theme.id == themeId
                    ListItem(
                        modifier = Modifier
                            .width(280.dp)
                            .toggleable(
                                enabled = theme.enabled,
                                value = themeIsSelected,
                                onValueChange = { themeId = theme.id },
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

                TextField(
                    value = gameIdText,
                    onValueChange = { gameIdText = it },
                    label = { Text("Game ID") },
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (newGameIdIsValid) {
                        postInput(
                            GameContract.Inputs.UpdateGameSettings(
                                themeId = themeId,
                                gameId = gameIdNumber!!,
                            )
                        )
                    }
                },
                enabled = newGameIdIsValid
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
        }
    )
}
