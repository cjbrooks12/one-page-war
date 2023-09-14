package com.copperleaf.onepagewar

import androidx.compose.ui.window.ComposeUIViewController
import com.copperleaf.onepagewar.di.AppInjectorImpl
import com.copperleaf.onepagewar.ui.main.MainUi
import platform.UIKit.UIViewController

public fun MainViewController(): UIViewController {
    return ComposeUIViewController {
        MainUi.App(AppInjectorImpl())
    }
}
