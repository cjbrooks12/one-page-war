package com.caseyjbrooks.onepagewar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.caseyjbrooks.onepagewar.di.AppInjectorImpl
import com.caseyjbrooks.onepagewar.ui.main.MainUi
import kotlinx.coroutines.MainScope

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainUi.App(AppInjectorImpl(MainScope()))
        }
    }
}
