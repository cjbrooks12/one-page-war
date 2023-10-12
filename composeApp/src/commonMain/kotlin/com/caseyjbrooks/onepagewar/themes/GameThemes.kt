package com.caseyjbrooks.onepagewar.themes

import com.caseyjbrooks.onepagewar.themes.galaxywars.GalaxyWarsTheme
import com.caseyjbrooks.onepagewar.themes.medieval.MedievalTheme
import com.caseyjbrooks.onepagewar.themes.modern.ModernTheme
import com.caseyjbrooks.onepagewar.themes.waterfight.WaterFightTheme
import com.caseyjbrooks.onepagewar.vm.game.models.Theme

internal object GameThemes {
    public const val WILD_VALUE = -1
    public const val EMPTY_VALUE = 0
    public val REMOVE_VALUE: Int? = null

    internal val defaultTheme: Theme = ModernTheme
    internal val allAvailableThemes: List<Theme> = listOf(
        ModernTheme,
        MedievalTheme,
        WaterFightTheme,
        GalaxyWarsTheme,
    )
}
