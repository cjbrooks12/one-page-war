package com.copperleaf.onepagewar

import androidx.compose.runtime.Composable
import com.copperleaf.ballast.debugger.BallastDebuggerClientConnection
import com.russhwolf.settings.Settings
import com.russhwolf.settings.StorageSettings
import io.ktor.client.engine.js.Js
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.CoroutineScope

internal actual object NativeUiUtils {

    internal actual val debug: Boolean = window.location.search.contains("debug=true")

    internal actual fun createDebuggerConnection(
        applicationCoroutineScope: CoroutineScope
    ): BallastDebuggerClientConnection<*> {
        return BallastDebuggerClientConnection(
            engineFactory = Js,
            applicationCoroutineScope = applicationCoroutineScope,
            host = "127.0.0.1",
        )
    }

    internal actual fun createSettings(scope: String): Settings {
        return NamedSettingsWrapper(
            name = scope,
            delegate = StorageSettings(localStorage),
        )
    }

    internal actual fun openUrl(url: String?) {
        url?.let { window.open(it) }
    }

    @Composable
    internal actual fun onBackPressedHandler(onBackPressed: () -> Unit) {
    }
}
