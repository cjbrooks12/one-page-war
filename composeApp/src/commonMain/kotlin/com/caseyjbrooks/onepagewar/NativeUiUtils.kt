package com.caseyjbrooks.onepagewar

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.copperleaf.ballast.debugger.BallastDebuggerClientConnection
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope

internal expect object NativeUiUtils {
    internal val debug: Boolean

    internal fun createDebuggerConnection(
        applicationCoroutineScope: CoroutineScope
    ): BallastDebuggerClientConnection<*>

    internal fun createSettings(scope: String): Settings

    internal fun openUrl(url: String?)

    @Composable
    internal fun onBackPressedHandler(onBackPressed: () -> Unit)

    internal fun textFieldModifier(): Modifier
}
