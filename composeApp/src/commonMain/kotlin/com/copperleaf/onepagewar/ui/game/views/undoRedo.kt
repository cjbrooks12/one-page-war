package com.copperleaf.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.copperleaf.onepagewar.vm.game.GameContract

@Suppress("UNUSED_PARAMETER")
@Composable
internal fun UndoRedo(
    state: GameContract.State,
    canUndo: Boolean,
    canRedo: Boolean,
    postInput: (GameContract.Inputs) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Button(
            onClick = { postInput(GameContract.Inputs.Undo) },
            enabled = canUndo,
            modifier = Modifier.weight(1f),
        ) {
            Text("Undo")
        }
        Button(
            onClick = { postInput(GameContract.Inputs.Redo) },
            enabled = canRedo,
            modifier = Modifier.weight(1f),
        ) {
            Text("Redo")
        }
    }
}
