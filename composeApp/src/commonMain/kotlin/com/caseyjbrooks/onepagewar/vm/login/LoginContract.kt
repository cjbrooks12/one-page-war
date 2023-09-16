package com.caseyjbrooks.onepagewar.vm.login

import com.caseyjbrooks.onepagewar.vm.login.models.LoginState
import kotlinx.serialization.Serializable

internal object LoginContract {
    @Serializable
    internal data class State(
        val loginState: LoginState = LoginState.LoggedOut,
        val errorMessage: String? = null
    )

    @Serializable
    internal sealed interface Inputs {
        @Serializable
        data object Initialize : Inputs
        @Serializable
        data class TryLogin(val password: String) : Inputs
    }

    @Serializable
    internal sealed interface Events {
        @Serializable
        data class NavigateTo(val route: String) : Events
    }
}
