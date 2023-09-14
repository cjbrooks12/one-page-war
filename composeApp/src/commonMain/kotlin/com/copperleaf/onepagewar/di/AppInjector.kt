package com.copperleaf.onepagewar.di

import com.copperleaf.ballast.navigation.vm.Router
import com.copperleaf.onepagewar.AppScreen
import com.copperleaf.onepagewar.vm.game.GameUndoController
import com.copperleaf.onepagewar.vm.game.GameViewModel
import com.copperleaf.onepagewar.vm.game.models.Theme
import com.copperleaf.onepagewar.vm.login.LoginViewModel
import com.copperleaf.onepagewar.vm.notfound.NotFoundViewModel
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
