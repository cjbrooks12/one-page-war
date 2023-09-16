package com.caseyjbrooks.onepagewar.vm.game

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.vm.Router
import com.caseyjbrooks.onepagewar.AppScreen

internal class GameEventHandler(
    private val router: Router<AppScreen>
) : EventHandler<
        GameContract.Inputs,
        GameContract.Events,
        GameContract.State> {
    override suspend fun EventHandlerScope<
            GameContract.Inputs,
            GameContract.Events,
            GameContract.State>.handleEvent(
        event: GameContract.Events
    ): Unit = when (event) {
        is GameContract.Events.NavigateTo -> {
            router.send(
                RouterContract.Inputs.GoToDestination(event.route)
            )
        }
    }
}
