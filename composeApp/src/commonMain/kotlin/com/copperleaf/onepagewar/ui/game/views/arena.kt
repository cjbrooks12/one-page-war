package com.copperleaf.onepagewar.ui.game.views

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.copperleaf.onepagewar.ui.VerticalDivider
import com.copperleaf.onepagewar.ui.calculatedContentColor
import com.copperleaf.onepagewar.vm.game.GameContract
import com.copperleaf.onepagewar.vm.game.models.Arena
import com.copperleaf.onepagewar.vm.game.models.ArenaData
import com.copperleaf.onepagewar.vm.game.models.Player
import com.copperleaf.onepagewar.vm.game.models.Theme

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
        ) {
            if (selectedTheme.specialMark != null) {
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

                Spacer(Modifier.width(16.dp))
            }

            for (i in 1..arena.numberOfValues) {
                ArenaValue(
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
        if (value != null) {
            if (focusedPlayer.bot && value == 0) {
                Icon(Icons.Default.Star, "Wild Value")
            } else {
                Text("$value", color = arena.color.calculatedContentColor)
            }
        }

        if (focusedTooltipArena == arena && focusedTooltipIndex == valueIndex) {
            ArenaTooltip(
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
        Card(
            modifier = Modifier
                .requiredSize(width = 48.dp * 5, height = 48.dp * 2),
            border = BorderStroke(width = Dp.Hairline, color = arena.color.calculatedContentColor),
            content = {
                Column(
                    Modifier
                        .requiredSize(width = 48.dp * 5, height = 48.dp * 2),
                ) {
                    Row(
                        modifier = Modifier
                            .requiredSize(width = 48.dp * 5, height = 48.dp),
                    ) {
                        val firstRowValues = 1..5
                        firstRowValues.forEach { index ->
                            Box(
                                Modifier
                                    .size(48.dp)
                                    .background(arena.color)
                                    .clickable {
                                        onValueChanged(index)
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                Text("${index}", color = arena.color.calculatedContentColor)
                            }
                            if (index != firstRowValues.last) {
                                VerticalDivider(
                                    color = arena.color.calculatedContentColor
                                )
                            }
                        }
                    }
                    Divider(color = arena.color.calculatedContentColor)
                    Row(
                        modifier = Modifier
                            .requiredSize(width = 48.dp * 5, height = 48.dp),
                    ) {
                        val secondRowValues = if (focusedPlayer.bot) {
                            listOf(6, 7, -1, 0, null)
                        } else {
                            listOf(6, 7, 8, 9, null)
                        }

                        secondRowValues.forEach { index ->
                            Box(
                                Modifier
                                    .size(48.dp)
                                    .background(arena.color)
                                    .clickable {
                                        onValueChanged(index)
                                    },
                                contentAlignment = Alignment.Center,
                            ) {
                                when (index) {
                                    null -> {
                                        // remove value button
                                        Icon(
                                            Icons.Default.RemoveCircle,
                                            "Clear Value",
                                            tint = arena.color.calculatedContentColor
                                        )
                                    }

                                    -1 -> {
                                        // no value displayed
                                    }

                                    0 -> {
                                        // bot's Star ability
                                        Icon(
                                            Icons.Default.Star,
                                            "Wild Value",
                                            tint = arena.color.calculatedContentColor
                                        )
                                    }

                                    else -> {
                                        Text("${index}", color = arena.color.calculatedContentColor)
                                    }
                                }
                            }
                            if (index != secondRowValues.last()) {
                                VerticalDivider(
                                    color = arena.color.calculatedContentColor
                                )
                            }
                        }
                    }
                }
            }
        )
    }
}

@Suppress("UNUSED_PARAMETER")
@Composable
private fun ArenaUse(
    arena: Arena,
    used: Boolean,
    onValueChanged: (Boolean) -> Unit,
) {
    Box(
        Modifier
            .size(36.dp)
            .background(Color.White)
            .clickable { onValueChanged(!used) },
        contentAlignment = Alignment.Center
    ) {
        if (used) {
            Icon(Icons.Default.Check, "", tint = Color.Black)
        }
    }
}
