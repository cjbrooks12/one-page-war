package com.copperleaf.onepagewar.vm.login

import com.russhwolf.settings.Settings
import com.russhwolf.settings.nullableString

internal class LoginPrefsImpl(
    settings: Settings
) : LoginPrefs {
    override var selectedThemeId: String? by settings.nullableString()
    override var savedPassword: String? by settings.nullableString()
}
