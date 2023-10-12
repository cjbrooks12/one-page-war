package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.caseyjbrooks.onepagewar.themes.GameThemes.EMPTY_VALUE
import com.caseyjbrooks.onepagewar.themes.GameThemes.REMOVE_VALUE
import com.caseyjbrooks.onepagewar.themes.GameThemes.WILD_VALUE
import com.caseyjbrooks.onepagewar.ui.VerticalDivider
import com.caseyjbrooks.onepagewar.ui.calculatedContentColor
import com.caseyjbrooks.onepagewar.vm.game.GameContract
import com.caseyjbrooks.onepagewar.vm.game.models.Arena
import com.caseyjbrooks.onepagewar.vm.game.models.ArenaData
import com.caseyjbrooks.onepagewar.vm.game.models.Player
import com.caseyjbrooks.onepagewar.vm.game.models.Theme

@Composable
internal fun Arenas(
    selectedTheme: Theme,
    state: GameContract.State,
    focusedPlayerIndex: Int,
    focusedPlayer: Player,
    postInput: (GameContract.Inputs) -> Unit,
) {
    var focusedTooltipArena by remember { mutableStateOf<Arena?>(null) }
    var focusedTooltipIndex by remember { mutableStateOf<Int?>(null) }

    selectedTheme.arenas.forEach { arena ->
        val arenaData = state.arenaData[arena.id] ?: ArenaData()
        val playerValues = arenaData.values.getOrNull(focusedPlayerIndex) ?: emptyList()
        val powerUses = arenaData.powerUses
        val specialMark = arenaData.specialMark

        ArenaView(
            selectedTheme = selectedTheme,
            arena = arena,
            state = state,

            focusedPlayer = focusedPlayer,
            playerValues = playerValues,
            powerUses = powerUses,
            specialMark = specialMark,

            postInput = postInput,

            focusedTooltipArena = focusedTooltipArena,
            focusedTooltipIndex = focusedTooltipIndex,
            setOpenedTooltip = { openedArena, index ->
                focusedTooltipArena = openedArena
                focusedTooltipIndex = index
            }
        )
    }
}

@Composable
private fun ArenaView(
    selectedTheme: Theme,
    arena: Arena,
    state: GameContract.State,

    focusedPlayer: Player,
    playerValues: List<Int?> = emptyList(),
    powerUses: List<Boolean> = emptyList(),
    specialMark: String?,

    focusedTooltipArena: Arena?,
    focusedTooltipIndex: Int?,
    setOpenedTooltip: (Arena?, Int?) -> Unit,

    postInput: (GameContract.Inputs) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(arena.name())
        Row(
            modifier = Modifier.padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val currentMoveIsFocusedPlayer = focusedPlayer.name == state.currentMove?.playerName
            val currentMoveIsThisArena = arena.id == state.currentMove?.arenaId

            val currentMoveDiceValue = state.diceValues.getOrNull(state.currentMove?.diceIndex ?: -1)
            val currentMoveHasDiceValue = currentMoveDiceValue != null

            val currentMoveContentColor = if (currentMoveIsThisArena && currentMoveIsFocusedPlayer) {
                MaterialTheme.colorScheme.primary
            } else {
                LocalContentColor.current
            }

            // IF it's the focused player who set the current move:
            //      highlight this area in primary color
            // OTHERWISE:
            //      keep it the default content color
            // IF the current move was on this arena:
            //      add a circular border around this area
            // OTHERWISE:
            //      Don't use a border
            // IF the current move has a dice value
            //      display the dice value above the dice icon
            // OTHERWISE:
            //      Don't display anything
            Box(
                Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .then(
                        if (currentMoveIsThisArena) {
                            Modifier.border(2.dp, currentMoveContentColor, CircleShape)
                        } else {
                            Modifier
                        }
                    )
                    .clickable { postInput(GameContract.Inputs.SetCurrentMove(focusedPlayer.name, arena.id)) },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Casino, "", tint = currentMoveContentColor.copy(alpha = 0.35f))

                if (currentMoveIsThisArena && currentMoveHasDiceValue) {
                    Text(
                        "$currentMoveDiceValue",
                        style = MaterialTheme.typography.headlineMedium,
                        color = currentMoveContentColor
                    )
                }
            }
            Spacer(Modifier.width(12.dp))

            for (i in 1..arena.numberOfValues) {
                ArenaValue(
                    selectedTheme = selectedTheme,
                    arena = arena,
                    focusedPlayer = focusedPlayer,
                    valueIndex = i,
                    value = playerValues.getOrNull(i - 1),

                    focusedTooltipArena = focusedTooltipArena,
                    focusedTooltipIndex = focusedTooltipIndex,
                    setOpenedTooltip = setOpenedTooltip,

                    postInput = postInput,
                )
            }

            if (selectedTheme.specialMark != null) {
                Spacer(Modifier.width(12.dp))

                Box(
                    Modifier
                        .size(48.dp)
                        .border(width = 2.dp, arena.color)
                        .clickable {
                            postInput(GameContract.Inputs.MarkArenaWithThemeSpecial(arena.id))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (specialMark != null) {
                        Text(specialMark)
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(arena.color)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(arena.powerTitle(), color = arena.color.calculatedContentColor, textAlign = TextAlign.Center)
            Row(
                modifier = Modifier.padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                for (i in 1..arena.numberOfPowerUses) {
                    ArenaUse(
                        selectedTheme = selectedTheme,
                        arena = arena,
                        used = powerUses.getOrElse(i - 1) { false },
                        onValueChanged = {
                            postInput(
                                GameContract.Inputs.SetArenaPowerUsed(
                                    arena.id,
                                    i,
                                    it
                                )
                            )
                        },
                    )
                }
            }
            Text(arena.powerDescription(), color = arena.color.calculatedContentColor, textAlign = TextAlign.Center)
        }

        Divider()
    }
}

@Composable
private fun ArenaValue(
    selectedTheme: Theme,
    arena: Arena,
    focusedPlayer: Player,
    valueIndex: Int,
    value: Int?,

    focusedTooltipArena: Arena?,
    focusedTooltipIndex: Int?,
    setOpenedTooltip: (Arena?, Int?) -> Unit,

    postInput: (GameContract.Inputs) -> Unit,
) {
    Box(
        Modifier
            .size(48.dp)
            .background(arena.color)
            .clickable {
                setOpenedTooltip(arena, valueIndex)
            },
        contentAlignment = Alignment.Center
    ) {
        when (value) {
            null, EMPTY_VALUE -> {

            }

            WILD_VALUE -> {
                Icon(Icons.Default.Star, "Wild Value")
            }

            else -> {
                Text("$value", color = arena.color.calculatedContentColor)
            }
        }

        if (focusedTooltipArena == arena && focusedTooltipIndex == valueIndex) {
            ArenaTooltip(
                selectedTheme = selectedTheme,
                arena = arena,
                focusedPlayer = focusedPlayer,
                onDismissRequest = {
                    setOpenedTooltip(null, null)
                },
                onValueChanged = {
                    setOpenedTooltip(null, null)
                    postInput(GameContract.Inputs.SetArenaValue(arena.id, focusedPlayer.name, valueIndex, it))
                }
            )
        }
    }
}

@Composable
private fun ArenaTooltip(
    selectedTheme: Theme,
    arena: Arena,
    focusedPlayer: Player,

    onDismissRequest: () -> Unit,
    onValueChanged: (Int?) -> Unit,
) {
    Popup(
        alignment = Alignment.BottomCenter,
        offset = IntOffset(0, 48),
        onDismissRequest = onDismissRequest,
    ) {
        val rows = if (focusedPlayer.bot) {
            selectedTheme.botDiceValues.chunked(5)
        } else {
            selectedTheme.playerDiceValues.chunked(5)
        }

        val height = 48.dp * rows.size

        Card(
            modifier = Modifier
                .requiredSize(width = 48.dp * 5, height = height),
            border = BorderStroke(width = Dp.Hairline, color = arena.color.calculatedContentColor),
            content = {
                Column(
                    Modifier
                        .requiredSize(width = 48.dp * 5, height),
                ) {
                    rows.forEachIndexed { rowIndex, rowValues ->
                        ArenaTooltipRow(
                            selectedTheme = selectedTheme,
                            arena = arena,
                            rowValues = rowValues,
                            onValueChanged = onValueChanged,
                        )
                        if (rowIndex != rows.size) {
                            Divider(color = arena.color.calculatedContentColor)
                        }
                    }
                }
            }
        )
    }
}

@Composable
private fun ArenaTooltipRow(
    selectedTheme: Theme,
    arena: Arena,

    rowValues: List<Int?>,

    onValueChanged: (Int?) -> Unit,
) {
    Row(
        modifier = Modifier
            .requiredSize(width = 48.dp * 5, height = 48.dp),
    ) {
        rowValues.forEachIndexed { columnIndex, columnValue ->
            ArenaTooltipColumn(
                selectedTheme = selectedTheme,
                arena = arena,
                columnValue = columnValue,
                onValueChanged = onValueChanged,
            )
            if (columnIndex != rowValues.size) {
                VerticalDivider(
                    color = arena.color.calculatedContentColor
                )
            }
        }
    }
}

@Composable
private fun ArenaTooltipColumn(
    selectedTheme: Theme,
    arena: Arena,

    columnValue: Int?,

    onValueChanged: (Int?) -> Unit,
) {
    Box(
        Modifier
            .size(48.dp)
            .background(arena.color)
            .clickable {
                onValueChanged(columnValue)
            },
        contentAlignment = Alignment.Center,
    ) {
        when (columnValue) {
            WILD_VALUE -> {
                Icon(
                    Icons.Default.Star,
                    "Wild Value",
                    tint = arena.color.calculatedContentColor
                )
            }

            EMPTY_VALUE -> {
                // no value displayed
            }

            REMOVE_VALUE -> {
                // remove value button
                Icon(
                    Icons.Default.RemoveCircle,
                    "Clear Value",
                    tint = arena.color.calculatedContentColor
                )
            }

            else -> {
                Text("$columnValue", color = arena.color.calculatedContentColor)
            }
        }
    }
}

@Suppress("UNUSED_PARAMETER")
@Composable
private fun ArenaUse(
    selectedTheme: Theme,
    arena: Arena,
    used: Boolean,
    onValueChanged: (Boolean) -> Unit,
) {
    Box(
        Modifier
            .size(36.dp)
            .clip(selectedTheme.powerShape)
            .background(Color.White)
            .clickable { onValueChanged(!used) },
        contentAlignment = Alignment.Center
    ) {
        if (used) {
            Icon(Icons.Default.Check, "", tint = Color.Black)
        }
    }
}
