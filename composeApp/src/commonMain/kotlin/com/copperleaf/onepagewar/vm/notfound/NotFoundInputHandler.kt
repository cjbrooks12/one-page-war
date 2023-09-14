package com.copperleaf.onepagewar.vm.notfound

import com.copperleaf.ballast.InputHandler
import com.copperleaf.ballast.InputHandlerScope
import com.copperleaf.ballast.navigation.routing.build
import com.copperleaf.ballast.navigation.routing.directions
import com.copperleaf.onepagewar.AppScreen

internal class NotFoundInputHandler : InputHandler<
        NotFoundContract.Inputs,
        NotFoundContract.Events,
        NotFoundContract.State> {
    override suspend fun InputHandlerScope<
            NotFoundContract.Inputs,
            NotFoundContract.Events,
            NotFoundContract.State>.handleInput(
        input: NotFoundContract.Inputs
    ) = when (input) {
        NotFoundContract.Inputs.GoHome -> {
            postEvent(
                NotFoundContract.Events.NavigateTo(
                    AppScreen.LogIn.directions().build()
                )
            )
        }
    }
}
