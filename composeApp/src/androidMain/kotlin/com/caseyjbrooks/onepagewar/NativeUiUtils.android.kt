package com.caseyjbrooks.onepagewar

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.copperleaf.ballast.debugger.BallastDebuggerClientConnection
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import io.ktor.client.engine.okhttp.OkHttp
import kotlinx.coroutines.CoroutineScope

internal actual object NativeUiUtils {

    internal actual val debug: Boolean = true

    internal actual fun createDebuggerConnection(
        applicationCoroutineScope: CoroutineScope
    ): BallastDebuggerClientConnection<*> {
        return BallastDebuggerClientConnection(
            engineFactory = OkHttp,
            applicationCoroutineScope = applicationCoroutineScope,
            host = "10.0.2.2",
        )
    }

    internal actual fun createSettings(scope: String): Settings {
        return SharedPreferencesSettings
            .Factory(MainApplication.INSTANCE)
            .create(scope)
    }

    internal actual fun openUrl(url: String?) {
        val uri = url?.let { Uri.parse(it) } ?: return
        val intent = Intent().apply {
            action = Intent.ACTION_VIEW
            data = uri
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        MainApplication.INSTANCE.startActivity(intent)
    }

    @Composable
    internal actual fun onBackPressedHandler(onBackPressed: () -> Unit) {
        BackHandler { onBackPressed() }
    }

    internal actual fun textFieldModifier(): Modifier {
        return Modifier
    }
}
