package com.copperleaf.onepagewar.vm.game

import com.copperleaf.ballast.savedstate.RestoreStateScope
import com.copperleaf.ballast.savedstate.SaveStateScope
import com.copperleaf.ballast.savedstate.SavedStateAdapter

internal class GameSavedStateAdapter(
    private val prefs: GamePrefs,
    private val initialGameId: Int?,
) : SavedStateAdapter<
        GameContract.Inputs,
        GameContract.Events,
        GameContract.State> {

    override suspend fun SaveStateScope<
            GameContract.Inputs,
            GameContract.Events,
            GameContract.State>.save() {
        saveAll {
            prefs.gameState = it
        }
    }

    override suspend fun RestoreStateScope<
            GameContract.Inputs,
            GameContract.Events,
            GameContract.State>.restore(): GameContract.State {
        val savedState = prefs.gameState

        return if (initialGameId != null) {
            savedState.copy(
                gameId = initialGameId,
                diceIsRolling = false,
                diceValues = emptyList(),
            )
        } else {
            savedState
        }
    }
}
