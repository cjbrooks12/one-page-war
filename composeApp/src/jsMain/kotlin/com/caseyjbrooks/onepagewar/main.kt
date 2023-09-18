package com.caseyjbrooks.onepagewar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.CanvasBasedWindow
import com.caseyjbrooks.onepagewar.di.AppInjectorImpl
import com.caseyjbrooks.onepagewar.resources.MR
import com.caseyjbrooks.onepagewar.ui.router.RouterUi
import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import org.jetbrains.skiko.wasm.onWasmReady
import org.w3c.dom.url.URLSearchParams
import kotlin.coroutines.resume

@OptIn(ExperimentalComposeUiApi::class)
public fun main() {
    val scope = MainScope()
    val wasmLoaded = scope.async {
        suspendCancellableCoroutine<Unit> { cont ->
            onWasmReady {
                cont.resume(Unit)
            }
        }
    }
    val stringsLoaded = scope.async {
//        MR.stringsLoader.getOrLoad()
    }

    scope.launch {
        wasmLoaded.await()
        stringsLoaded.await()

        val urlParams = URLSearchParams(window.location.search)

        CanvasBasedWindow(MR.strings.app_name()) {
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.TopCenter,
            ) {
                if(this.maxWidth <= 480.dp) {
                    RouterUi.App(AppInjectorImpl(scope, urlParams.get("password")))
                } else {
                    Box(
                        modifier = Modifier
                            .requiredWidth(480.dp)
                            .background(MaterialTheme.colorScheme.surface)
                            .border(width = Dp.Hairline, color = MaterialTheme.colorScheme.onSurface)
                    ) {
                        RouterUi.App(AppInjectorImpl(scope, urlParams.get("password")))
                    }
                }
            }
        }
    }
}
