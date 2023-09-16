package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.caseyjbrooks.onepagewar.vm.game.GameContract
import com.caseyjbrooks.onepagewar.vm.game.models.Player

@Composable
internal fun PlayerDetails(
    state: GameContract.State,
    focusedPlayerIndex: Int,
    focusedPlayer: Player,

    postInput: (GameContract.Inputs) -> Unit,
) {
    when (state.players.size) {
        0 -> {
            // game not set up correctly
        }

        else -> {
            ListItem(
                leadingContent = {
                    if (focusedPlayer.bot) {
                        Icon(Icons.Default.SmartToy, "Bot")
                    } else {
                        Icon(Icons.Default.Person, "Human")
                    }
                },
                headlineContent = {
                    Text(focusedPlayer.name)
                },
                supportingContent = {
                    val numberOfTurnsTaken = state
                        .arenaData.values
                        .map { it.values.getOrNull(focusedPlayerIndex) ?: emptyList() }
                        .sumOf { it.filterNotNull().size }

                    if(numberOfTurnsTaken == 1) {
                        Text("$numberOfTurnsTaken turn")
                    } else {
                        Text("$numberOfTurnsTaken turns")
                    }
                },
                trailingContent = {
                    PlayerSettingsMenu(state, focusedPlayer, postInput)
                }
            )

        }
    }
}

