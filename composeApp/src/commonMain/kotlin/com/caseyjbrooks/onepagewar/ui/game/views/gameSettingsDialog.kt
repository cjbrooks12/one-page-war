package com.caseyjbrooks.onepagewar.ui.game.views

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.TextFieldValue
import com.caseyjbrooks.onepagewar.NativeUiUtils
import com.caseyjbrooks.onepagewar.vm.game.GameContract
import com.caseyjbrooks.onepagewar.vm.game.models.Theme

@Composable
internal fun GameSettingsDialog(
    selectedTheme: Theme,

    currentGameId: Int,
    onDismissRequest: () -> Unit,
    postInput: (GameContract.Inputs) -> Unit,
) {
    var themeId by remember(selectedTheme) { mutableStateOf(selectedTheme.id) }

    var gameIdText by remember(currentGameId) { mutableStateOf(TextFieldValue("$currentGameId")) }
    val gameIdNumber = gameIdText.text.toIntOrNull()
    val newGameIdIsValid = gameIdNumber != null && gameIdNumber in 1000..10_000

    AlertDialog(
        title = {
            Text("Game Settings")
        },
        onDismissRequest = onDismissRequest,
        text = {
            Column {
                TextField(
                    value = gameIdText,
                    onValueChange = { gameIdText = it },
                    label = { Text("Game ID") },
                    modifier = NativeUiUtils.textFieldModifier(),
                )
                ThemeSelector(
                    currentThemeId = themeId,
                    setThemeId = { themeId = it },
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (newGameIdIsValid) {
                        postInput(
                            GameContract.Inputs.UpdateGameSettings(
                                themeId = themeId,
                                gameId = gameIdNumber!!,
                            )
                        )
                    }
                },
                enabled = newGameIdIsValid
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            OutlinedButton(
                onClick = { onDismissRequest() },
            ) {
                Text("Close")
            }
        }
    )
}
