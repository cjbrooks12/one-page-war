package com.copperleaf.onepagewar

import android.content.Intent
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.copperleaf.ballast.debugger.BallastDebuggerClientConnection
import com.russhwolf.settings.Settings
import kotlinx.coroutines.CoroutineScope

internal actual object NativeUiUtils {

    internal actual fun createDebuggerConnection(
        applicationCoroutineScope: CoroutineScope
    ): BallastDebuggerClientConnection<*> {
        TODO("Not yet implemented")
    }

    internal actual fun createSettings(scope: String): Settings {
        return Settings()
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

}
