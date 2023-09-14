package com.copperleaf.onepagewar.ui

import androidx.compose.runtime.Composable
import java.awt.Desktop
import java.net.URI

internal actual object NativeUiUtils {
    internal actual fun openUrl(url: String?) {
        val uri = url?.let { URI.create(it) } ?: return
        Desktop.getDesktop().browse(uri)
    }

    @Composable
    internal actual fun onBackPressedHandler(onBackPressed: () -> Unit) {
    }
}
