package com.copperleaf.onepagewar.vm.notfound

import kotlinx.serialization.Serializable

internal object NotFoundContract {
    @Serializable
    internal class State()

    @Serializable
    internal sealed interface Inputs {
        @Serializable
        data object GoHome : Inputs
    }

    @Serializable
    internal sealed interface Events {
        @Serializable
        data class NavigateTo(val route: String) : Events
    }
}
