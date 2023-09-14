package com.copperleaf.onepagewar.vm.game

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import com.copperleaf.ballast.navigation.routing.pathParameter
import com.copperleaf.ballast.navigation.routing.queryParameter
import com.copperleaf.ballast.undo.BallastUndoInterceptor
import com.copperleaf.onepagewar.AppScreen
import com.copperleaf.onepagewar.vm.game.models.Arena
import com.copperleaf.onepagewar.vm.game.models.ArenaData
import com.copperleaf.onepagewar.vm.game.models.Player
import com.copperleaf.onepagewar.vm.game.models.Theme
import kotlinx.coroutines.delay
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

internal class GameInputHandler(
    private val theme: Theme,
    private val diceRollDelay: Duration = 0.5.seconds,
) : InputHandler<
        GameContract.Inputs,
        GameContract.Events,
        GameContract.State> {
    override suspend fun InputHandlerScope<
            GameContract.Inputs,
            GameContract.Events,
            GameContract.State>.handleInput(
        input: GameContract.Inputs
    ): Unit = when (input) {
        // ----------- //
        // Manage Game //
        // ----------- //

        is GameContract.Inputs.NewGame -> {
            updateState { GameContract.State() }
        }

        is GameContract.Inputs.UpdateGameSettings -> {
            postEvent(
                GameContract.Events.NavigateTo(
                    AppScreen.PlayGame
                        .directions()
                        .pathParameter("themeId", input.themeId)
                        .queryParameter("initialGameId", input.gameId.toString())
                        .build()
                )
            )
        }

        is GameContract.Inputs.AddPlayer -> {
            updateState {
                it.copy(
                    players = it.players + Player(input.playerName, false)
                )
            }
        }

        is GameContract.Inputs.RemovePlayer -> {
            val currentState = getCurrentState()
            val indexedPlayer = currentState.players.withIndex().singleOrNull { it.value.name == input.playerName }
            checkNotNull(indexedPlayer) { "Player '${input.playerName} not found!" }
            val (playerIndex, _) = indexedPlayer

            updateState {
                it.copy(
                    players = it
                        .players
                        .toMutableList()
                        .apply {
                            removeAt(playerIndex)
                        }
                        .toList()
                )
            }
        }

        is GameContract.Inputs.UpdatePlayerDetails -> {
            val currentState = getCurrentState()
            val indexedPlayer = currentState.players.withIndex().singleOrNull { it.value.name == input.oldPlayerName }
            checkNotNull(indexedPlayer) { "Player '${input.oldPlayerName} not found!" }
            val (playerIndex, _) = indexedPlayer

            updateState {
                it.copy(
                    players = it.players.updateInList(
                        listSize = it.players.size,
                        index = playerIndex,
                        createNewValue = { Player(input.newPlayerName, input.bot) },
                        updateValue = { Player(input.newPlayerName, input.bot) },
                    )
                )
            }
        }

        // --------- //
        // Roll Dice //
        // --------- //

        is GameContract.Inputs.RollDice -> {
            val currentState = updateStateAndGet { it.copy(diceIsRolling = true) }
            sideJob("RollDice") {
                delay(diceRollDelay)
                val rolledValue = currentState.getNextDiceValue()
                postInput(GameContract.Inputs.DiceValueUpdated(rolledValue))
            }
        }

        is GameContract.Inputs.ClearDiceRolls -> {
            updateState { it.copy(diceIsRolling = false, diceValues = emptyList()) }
        }

        is GameContract.Inputs.DiceValueUpdated -> {
            updateState { it.copy(diceIsRolling = false, diceValues = it.diceValues + input.value) }
        }

        // --------- //
        // Undo/Redo //
        // --------- //

        is GameContract.Inputs.Undo -> {
            sideJob("undoRedo") {
                getInterceptor(BallastUndoInterceptor.Key).undo()
            }
        }

        is GameContract.Inputs.Redo -> {
            sideJob("undoRedo") {
                getInterceptor(BallastUndoInterceptor.Key).redo()
            }
        }

        // ----------------- //
        // Update Game State //
        // ----------------- //

        is GameContract.Inputs.SetArenaValue -> {
            val arena = theme.arenas.singleOrNull { it.id == input.arenaId }
            checkNotNull(arena) { "Arena with id '${input.arenaId}' not found in theme '${theme.id}" }

            val currentState = getCurrentState()

            val indexedPlayer = currentState.players.withIndex().singleOrNull { it.value.name == input.playerName }
            checkNotNull(indexedPlayer) { "Player '${input.playerName} not found!" }
            val (playerIndex, player) = indexedPlayer

            check(input.valueIndex in 1..arena.numberOfValues) { "${arena.id} can only hold ${arena.numberOfValues} values!" }

            if (player.bot) {
                // for bots, a 0 represents a starred value
                check(input.value == null || input.value in 0..7) { "Bot value must be 0-7!" }
            } else {
                check(input.value == null || input.value in 1..9) { "Human dice value must be 1-9!" }
            }

            updateState { state ->
                state.copy(
                    arenaData = state.arenaData.updateInMap(
                        key = arena.id,
                        createNewValue = { ArenaData() },
                        updateValue = { arenaData ->
                            arenaData.copy(
                                values = arenaData.values.setValueAtIndices(
                                    arena = arena,
                                    players = state.players,
                                    playerIndex = playerIndex,
                                    valueIndex = input.valueIndex,
                                    inputValue = input.value,
                                ),
                            )
                        }
                    )
                )
            }
        }

        is GameContract.Inputs.SetArenaPowerUsed -> {
            val arena = theme.arenas.singleOrNull { it.id == input.arenaId }
            checkNotNull(arena) { "Arena with id '${input.arenaId}' not found in theme '${theme.id}" }

            check(input.index in 1..arena.numberOfPowerUses) { "${arena.id} can only hold ${arena.numberOfPowerUses} uses!" }

            updateState { state ->
                state.copy(
                    arenaData = state.arenaData.updateInMap(
                        key = arena.id,
                        createNewValue = { ArenaData() },
                        updateValue = { arenaData ->
                            arenaData.copy(
                                powerUses = arenaData.powerUses.setUsedAtIndex(
                                    arena = arena,
                                    inputIndex = input.index,
                                    inputUsed = input.used,
                                ),
                            )
                        }
                    )
                )
            }
        }

        is GameContract.Inputs.MarkArenaWithThemeSpecial -> {
            val arena = theme.arenas.singleOrNull { it.id == input.arenaId }
            checkNotNull(arena) { "Arena with id '${input.arenaId}' not found in theme '${theme.id}" }

            updateState { state ->
                state.copy(
                    arenaData = state.arenaData.updateInMap(
                        key = arena.id,
                        createNewValue = { ArenaData() },
                        updateValue = { arenaData ->
                            if(arenaData.specialMark == null) {
                                arenaData.copy(specialMark = theme.specialMark)
                            } else {
                                arenaData.copy(specialMark = null)
                            }
                        }
                    )
                )
            }
        }
    }

// Set dice value
// ---------------------------------------------------------------------------------------------------------------------

    private fun List<List<Int?>>.setValueAtIndices(
        arena: Arena,
        players: List<Player>,
        playerIndex: Int,
        valueIndex: Int,
        inputValue: Int?
    ): List<List<Int?>> {
        return updateInList(
            listSize = players.size,
            index = playerIndex,
            createNewValue = { emptyList() },
            updateValue = {
                it.updateInList(
                    listSize = arena.numberOfValues,
                    index = valueIndex - 1,
                    createNewValue = { null },
                    updateValue = {
                        inputValue
                    }
                )
            }
        )
    }

// Mark a power as checked
// ---------------------------------------------------------------------------------------------------------------------

    private fun List<Boolean>.setUsedAtIndex(
        arena: Arena,
        inputIndex: Int,
        inputUsed: Boolean
    ): List<Boolean> {
        return updateInList(
            listSize = arena.numberOfPowerUses,
            index = inputIndex - 1,
            createNewValue = { false },
            updateValue = {
                inputUsed
            }
        )
    }

// Utils
// ---------------------------------------------------------------------------------------------------------------------

    private fun <Value> List<Value>.updateInList(
        listSize: Int,
        index: Int,
        createNewValue: () -> Value,
        updateValue: (Value) -> Value,
    ): List<Value> {
        val mutableList = this.toMutableList()

        if (mutableList.size < listSize) {
            for (i in mutableList.size until listSize) {
                mutableList.add(createNewValue())
            }
        }

        mutableList[index] = updateValue(mutableList[index])

        return mutableList.toList()
    }

    private fun <Key, Value> Map<Key, Value>.updateInMap(
        key: Key,
        createNewValue: () -> Value,
        updateValue: (Value) -> Value,
    ): Map<Key, Value> {
        val mutableMap = this.toMutableMap()
        val originalValue = mutableMap.getOrPut(key) { createNewValue() }
        mutableMap[key] = updateValue(originalValue)

        return mutableMap.toMap()
    }
}
