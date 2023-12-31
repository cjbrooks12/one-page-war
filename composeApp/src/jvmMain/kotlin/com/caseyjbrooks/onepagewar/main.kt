package com.caseyjbrooks.onepagewar

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.caseyjbrooks.onepagewar.di.AppInjectorImpl
import com.caseyjbrooks.onepagewar.ui.main.MainUi

public fun main() {
    application {
        Window(
            title = "Onepagewar",
            state = rememberWindowState(width = 800.dp, height = 600.dp),
            onCloseRequest = ::exitApplication,
        ) {
            MainUi.App(AppInjectorImpl())
        }
    }
}
