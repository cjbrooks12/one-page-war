package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
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
import com.caseyjbrooks.onepagewar.vm.game.models.Player

@Composable
internal fun PlayerSettingsMenu(
    state: GameContract.State,
    focusedPlayer: Player,

    postInput: (GameContract.Inputs) -> Unit
) {
    var extraOptionsMenuVisible by remember { mutableStateOf(false) }
    var playerDetailsDialogIsOpen by remember { mutableStateOf(false) }

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
                text = { Text("Player Details") },
                onClick = {
                    playerDetailsDialogIsOpen = true
                    extraOptionsMenuVisible = false
                },
                leadingIcon = { Icon(Icons.Default.Settings, "Player Details") }
            )
//            DropdownMenuItem(
//                text = { Text("Remove Player") },
//                onClick = {
//                    extraOptionsMenuVisible = false
//                    postInput(GameContract.Inputs.RemovePlayer(focusedPlayer.name))
//                },
//                leadingIcon = { Icon(Icons.Default.RemoveCircle, "Remove Player") }
//            )
        }
    }

    if (playerDetailsDialogIsOpen) {
        PlayerSettingsDialog(
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
