package com.copperleaf.onepagewar.ui.game

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.copperleaf.onepagewar.resources.MR
import com.copperleaf.onepagewar.themes.GameThemes
import com.copperleaf.onepagewar.ui.LocalInjector
import com.copperleaf.onepagewar.ui.game.views.Arenas
import com.copperleaf.onepagewar.ui.game.views.DiceRoller
import com.copperleaf.onepagewar.ui.game.views.Header
import com.copperleaf.onepagewar.ui.game.views.PlayerDetails
import com.copperleaf.onepagewar.ui.game.views.PlayerTabRow
import com.copperleaf.onepagewar.ui.game.views.UndoRedo
import com.copperleaf.onepagewar.vm.game.GameContract
import com.copperleaf.onepagewar.vm.game.models.Theme
import kotlin.math.min

internal object GameUi {

    @Composable
    internal fun Screen(themeId: String, initialGameId: Int?) {
        val selectedTheme = GameThemes.allAvailableThemes.singleOrNull() { it.id == themeId }

        if (selectedTheme == null) {
            Text(MR.strings.not_found())
            return;
        }

        val injector = LocalInjector.current
        val coroutineScope = rememberCoroutineScope()
        val (vm, undoController) = remember(coroutineScope, selectedTheme, initialGameId) {
            injector.gameViewModel(coroutineScope, selectedTheme, initialGameId)
        }

        val state by vm.observeStates().collectAsState()
        val canUndo by undoController.isUndoAvailable.collectAsState(false)
        val canRedo by undoController.isRedoAvailable.collectAsState(false)

        Screen(selectedTheme, state, canUndo, canRedo, vm::trySend)
    }

    @Composable
    private fun Screen(
        selectedTheme: Theme,
        state: GameContract.State,
        canUndo: Boolean,
        canRedo: Boolean,
        postInput: (GameContract.Inputs) -> Unit,
    ) {
        Column(Modifier.fillMaxSize()) {
            Header(
                selectedTheme = selectedTheme,
                state = state,
                postInput = postInput,
            )
            DiceRoller(
                state = state,
                postInput = postInput,
            )
            UndoRedo(
                state = state,
                canUndo = canUndo,
                canRedo = canRedo,
                postInput = postInput,
            )

            Divider()

            var focusedPlayerIndex by remember { mutableStateOf(0) }
            val actualIndex = min(focusedPlayerIndex, state.players.lastIndex)

            val focusedPlayer = state.players.getOrNull(actualIndex)

            PlayerTabRow(
                state = state,
                focusedPlayerIndex = actualIndex,
                focusedPlayerChanged = { focusedPlayerIndex = it },
            )

            Column(
                Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(top = 24.dp, bottom = 8.dp, start = 16.dp, end = 16.dp)
            ) {
                if (focusedPlayer != null) {
                    PlayerDetails(
                        state = state,
                        focusedPlayer = focusedPlayer,
                        postInput = postInput,
                    )
                    Divider()

                    Arenas(
                        selectedTheme = selectedTheme,
                        state = state,
                        focusedPlayerIndex = actualIndex,
                        focusedPlayer = focusedPlayer,
                        postInput = postInput,
                    )
                }
            }
        }
    }
}
