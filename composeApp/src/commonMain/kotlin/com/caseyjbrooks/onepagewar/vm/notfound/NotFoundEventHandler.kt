package com.caseyjbrooks.onepagewar.vm.notfound

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.vm.Router
import com.caseyjbrooks.onepagewar.AppScreen

internal class NotFoundEventHandler(
    private val router: Router<AppScreen>
) : EventHandler<
        NotFoundContract.Inputs,
        NotFoundContract.Events,
        NotFoundContract.State> {
    override suspend fun EventHandlerScope<
            NotFoundContract.Inputs,
            NotFoundContract.Events,
            NotFoundContract.State>.handleEvent(
        event: NotFoundContract.Events
    ): Unit = when (event) {
        is NotFoundContract.Events.NavigateTo -> {
            router.send(
                RouterContract.Inputs.ReplaceTopDestination(
                    event.route
                )
            )
        }
    }
}
