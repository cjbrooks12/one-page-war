package com.copperleaf.onepagewar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.copperleaf.onepagewar.di.AppInjectorImpl
import com.copperleaf.onepagewar.ui.main.MainUi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainUi.App(AppInjectorImpl())
        }
    }
}
