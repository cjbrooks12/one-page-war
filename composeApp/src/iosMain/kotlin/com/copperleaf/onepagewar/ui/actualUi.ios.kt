package com.copperleaf.onepagewar.ui

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

internal actual object NativeUiUtils {
    internal actual fun openUrl(url: String?) {
        val nsUrl = url?.let { NSURL.URLWithString(it) } ?: return
        UIApplication.sharedApplication.openURL(nsUrl)
    }

    @Composable
    internal actual fun onBackPressedHandler(onBackPressed: () -> Unit) {
    }
}
