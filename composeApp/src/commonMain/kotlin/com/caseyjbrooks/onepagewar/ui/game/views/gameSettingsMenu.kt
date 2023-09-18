package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.caseyjbrooks.onepagewar.vm.game.GameContract
import com.caseyjbrooks.onepagewar.vm.game.models.Theme

@Composable
internal fun GameOverflowDropdownMenu(
    selectedTheme: Theme,
    state: GameContract.State,
    postInput: (GameContract.Inputs) -> Unit,
) {
    var extraOptionsMenuVisible by remember { mutableStateOf(false) }
    var gameIdPopupVisible by remember { mutableStateOf(false) }
    var newPlayerPopupVisible by remember { mutableStateOf(false) }

    Box {
        IconButton(
            onClick = { extraOptionsMenuVisible = !extraOptionsMenuVisible },
        ) {
            Icon(Icons.Default.MoreVert, "Game Settings")
        }
        DropdownMenu(
            expanded = extraOptionsMenuVisible,
            onDismissRequest = { extraOptionsMenuVisible = false },
        ) {
            DropdownMenuItem(
                text = { Text("Game Info") },
                onClick = {
                    gameIdPopupVisible = true
                    extraOptionsMenuVisible = false
                },
                leadingIcon = { Icon(Icons.Default.Info, "Game Info") }
            )
            DropdownMenuItem(
                text = { Text("New Game") },
                onClick = {
                    postInput(GameContract.Inputs.NewGame)
                    extraOptionsMenuVisible = false
                },
                leadingIcon = { Icon(Icons.Default.RestartAlt, "New Game") }
            )
            DropdownMenuItem(
                text = { Text("Add Player (coming soon)") },
                onClick = {
                    newPlayerPopupVisible = true
                    extraOptionsMenuVisible = false
                },
                leadingIcon = { Icon(Icons.Default.PersonAdd, "Add Player") },
                enabled = false
            )
        }
    }

    if (gameIdPopupVisible) {
        GameSettingsDialog(
            selectedTheme = selectedTheme,
            currentGameId = state.gameId,
            onDismissRequest = { gameIdPopupVisible = false },
            postInput = {
                gameIdPopupVisible = false
                postInput(it)
            }
        )
    }

    if (newPlayerPopupVisible) {
        PlayerSettingsDialog(
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
