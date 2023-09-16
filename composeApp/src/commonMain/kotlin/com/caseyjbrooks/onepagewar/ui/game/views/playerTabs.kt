package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import com.caseyjbrooks.onepagewar.vm.game.GameContract

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
                    if(player.name == state.currentMove?.playerName) {
                        Icon(Icons.Default.Casino, "Turn Leader")
                    }

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
