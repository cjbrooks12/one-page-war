package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Redo
import androidx.compose.material.icons.filled.Undo
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.caseyjbrooks.onepagewar.vm.game.GameContract

@Suppress("UNUSED_PARAMETER")
@Composable
internal fun UndoRedo(
    modifier: Modifier = Modifier,
    state: GameContract.State,
    canUndo: Boolean,
    canRedo: Boolean,
    postInput: (GameContract.Inputs) -> Unit,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        IconButton(
            onClick = { postInput(GameContract.Inputs.Undo) },
            enabled = canUndo,
        ) {
            Icon(Icons.Default.Undo, "Undo")
        }
        IconButton(
            onClick = { postInput(GameContract.Inputs.Redo) },
            enabled = canRedo,
        ) {
            Icon(Icons.Default.Redo, "Redo")
        }
    }
}
