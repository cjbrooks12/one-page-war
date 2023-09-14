package com.copperleaf.onepagewar.vm.login

import com.copperleaf.ballast.EventHandler
import com.copperleaf.ballast.EventHandlerScope
import com.copperleaf.ballast.navigation.routing.RouterContract
import com.copperleaf.ballast.navigation.vm.Router
import com.copperleaf.onepagewar.AppScreen

internal class LoginEventHandler(
    private val router: Router<AppScreen>
) : EventHandler<
        LoginContract.Inputs,
        LoginContract.Events,
        LoginContract.State> {
    override suspend fun EventHandlerScope<
            LoginContract.Inputs,
            LoginContract.Events,
            LoginContract.State>.handleEvent(
        event: LoginContract.Events
    ): Unit = when (event) {
        is LoginContract.Events.NavigateTo -> {
            router.send(
                RouterContract.Inputs.GoToDestination(event.route)
            )
        }
    }
}
