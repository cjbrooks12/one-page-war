package com.copperleaf.onepagewar.vm.login

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import com.copperleaf.ballast.navigation.routing.pathParameter
import com.copperleaf.onepagewar.AppScreen
import com.copperleaf.onepagewar.resources.StringResource
import com.copperleaf.onepagewar.themes.GameThemes

internal class LoginInputHandler(
    private val hardcodedPassword: StringResource,
    private val prefs: LoginPrefs,
) : InputHandler<
        LoginContract.Inputs,
        LoginContract.Events,
        LoginContract.State> {
    override suspend fun InputHandlerScope<
            LoginContract.Inputs,
            LoginContract.Events,
            LoginContract.State>.handleInput(
        input: LoginContract.Inputs
    ): Unit = when (input) {
        is LoginContract.Inputs.Initialize -> {
            if (prefs.savedPassword == hardcodedPassword()) {
                postEvent(
                    LoginContract.Events.NavigateTo(
                        AppScreen.PlayGame
                            .directions()
                            .pathParameter("themeId", prefs.selectedThemeId ?: GameThemes.defaultTheme.id)
                            .build()
                    )
                )
            } else {
                noOp()
            }
        }

        is LoginContract.Inputs.TryLogin -> {
            if (input.password == hardcodedPassword()) {
                prefs.savedPassword = input.password
                postEvent(
                    LoginContract.Events.NavigateTo(
                        AppScreen.PlayGame
                            .directions()
                            .pathParameter("themeId", prefs.selectedThemeId ?: GameThemes.defaultTheme.id)
                            .build()
                    )
                )
            } else {
                prefs.savedPassword = null
                updateState {
                    it.copy(errorMessage = "Incorrect password")
                }
            }
        }
    }
}
