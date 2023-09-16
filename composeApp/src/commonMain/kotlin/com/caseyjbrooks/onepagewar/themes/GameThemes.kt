package com.caseyjbrooks.onepagewar.themes

import com.caseyjbrooks.onepagewar.themes.medieval.MedievalGameTheme
import com.caseyjbrooks.onepagewar.themes.modern.ModernGameTheme
import com.caseyjbrooks.onepagewar.themes.waterfight.WaterFightGameTheme
import com.caseyjbrooks.onepagewar.vm.game.models.Theme

internal object GameThemes {
    internal val defaultTheme: Theme = ModernGameTheme
    internal val allAvailableThemes: List<Theme> = listOf(
        ModernGameTheme,
        MedievalGameTheme,
        WaterFightGameTheme,
    )
}
