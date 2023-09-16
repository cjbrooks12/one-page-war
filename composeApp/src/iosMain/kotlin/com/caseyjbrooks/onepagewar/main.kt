package com.caseyjbrooks.onepagewar

import androidx.compose.ui.window.ComposeUIViewController
import com.caseyjbrooks.onepagewar.di.AppInjectorImpl
import com.caseyjbrooks.onepagewar.ui.main.MainUi
import platform.UIKit.UIViewController

public fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        MainUi.App(AppInjectorImpl())
    }
}
