package com.caseyjbrooks.onepagewar.di

import com.copperleaf.ballast.navigation.vm.Router
import com.caseyjbrooks.onepagewar.AppScreen
import com.caseyjbrooks.onepagewar.vm.game.GameUndoController
import com.caseyjbrooks.onepagewar.vm.game.GameViewModel
import com.caseyjbrooks.onepagewar.vm.game.models.Theme
import com.caseyjbrooks.onepagewar.vm.login.LoginViewModel
import com.caseyjbrooks.onepagewar.vm.notfound.NotFoundViewModel
import kotlinx.coroutines.CoroutineScope

internal interface AppInjector {
    val router: Router<AppScreen>

    fun notFoundViewModel(coroutineScope: CoroutineScope): NotFoundViewModel
    fun loginViewModel(coroutineScope: CoroutineScope): LoginViewModel
    fun gameViewModel(
        coroutineScope: CoroutineScope,
        theme: Theme,
        initialGameId: Int?
    ): Pair<GameViewModel, GameUndoController>
}
