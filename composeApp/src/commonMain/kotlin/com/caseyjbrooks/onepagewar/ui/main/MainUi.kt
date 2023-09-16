package com.caseyjbrooks.onepagewar.ui.main

import androidx.compose.animation.AnimatedContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.routing.optionalIntQuery
import com.copperleaf.ballast.navigation.routing.renderCurrentDestination
import com.copperleaf.ballast.navigation.routing.stringPath
import com.caseyjbrooks.onepagewar.AppScreen
import com.caseyjbrooks.onepagewar.AppTheme
import com.caseyjbrooks.onepagewar.NativeUiUtils
import com.caseyjbrooks.onepagewar.di.AppInjector
import com.caseyjbrooks.onepagewar.ui.LocalInjector
import com.caseyjbrooks.onepagewar.ui.game.GameUi
import com.caseyjbrooks.onepagewar.ui.login.LoginUi
import com.caseyjbrooks.onepagewar.ui.notfound.NotFoundUi

internal object MainUi {

    @Composable
    internal fun App(injector: AppInjector) = AppTheme {
        CompositionLocalProvider(LocalInjector provides injector) {
            Router()
        }
    }

    @Composable
    internal fun Router() {
        val injector = LocalInjector.current
        val vm = remember(injector) { injector.router }

        val routerState by vm.observeStates().collectAsState()

        Router(routerState, vm::trySend)
    }

    @Composable
    private fun Router(
        routerState: RouterContract.State<AppScreen>,
        postInput: (RouterContract.Inputs<AppScreen>) -> Unit
    ) {
        NativeUiUtils.onBackPressedHandler {
            postInput(RouterContract.Inputs.GoBack())
        }

        routerState.renderCurrentDestination(
            route = { currentScreen ->
                val match = this
                AnimatedContent(currentScreen) { screen ->
                    when (screen) {
                        AppScreen.LogIn -> {
                            LoginUi.Screen()
                        }

                        AppScreen.PlayGame -> {
                            val themeId by match.stringPath()
                            val initialGameId by match.optionalIntQuery()
                            GameUi.Screen(themeId, initialGameId)
                        }
                    }
                }
            },
            notFound = {
                NotFoundUi.Screen()
            }
        )
    }
}
