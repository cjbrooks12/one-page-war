package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Casino
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.caseyjbrooks.onepagewar.vm.game.GameContract

@Composable
internal fun DiceRollerButton(
    state: GameContract.State,
    postInput: (GameContract.Inputs) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier.size(56.dp)) {
        if (state.diceIsRolling) {
            CircularProgressIndicator()
        } else {
            FloatingActionButton(
                onClick = { postInput(GameContract.Inputs.RollDice) },
            ) {
                Icon(Icons.Default.Casino, "Roll Dice")
            }
        }
    }
}

@Composable
internal fun DiceRollerValues(
    state: GameContract.State,
    postInput: (GameContract.Inputs) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier.horizontalScroll(rememberScrollState())
    ) {
        state
            .diceValues
            .withIndex()
            .reversed()
            .forEach { (index, value) ->
                Column(
                    modifier = Modifier.padding(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("[${index + 1}]", style = MaterialTheme.typography.bodySmall)
                    Text("$value", style = MaterialTheme.typography.bodyLarge)
                }
            }
    }
}
