package com.caseyjbrooks.onepagewar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.caseyjbrooks.onepagewar.di.AppInjectorImpl
import com.caseyjbrooks.onepagewar.resources.MR
import com.caseyjbrooks.onepagewar.ui.router.RouterUi
import kotlinx.coroutines.MainScope

public class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RouterUi.App(AppInjectorImpl(MainScope(), MR.strings.hardcodedPassword()))
        }
    }
}
