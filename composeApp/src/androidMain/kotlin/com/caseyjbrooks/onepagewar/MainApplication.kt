package com.caseyjbrooks.onepagewar

import android.app.Application

public class MainApplication : Application() {
    public companion object {
        public lateinit var INSTANCE: MainApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}
