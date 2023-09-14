package com.copperleaf.onepagewar.themes

import com.copperleaf.onepagewar.themes.medieval.MedievalGameTheme
import com.copperleaf.onepagewar.themes.modern.ModernGameTheme
import com.copperleaf.onepagewar.themes.waterfight.WaterFightGameTheme
import com.copperleaf.onepagewar.vm.game.models.Theme

internal object GameThemes {
    internal val defaultTheme: Theme = ModernGameTheme
    internal val allAvailableThemes: List<Theme> = listOf(
        ModernGameTheme,
        MedievalGameTheme,
        WaterFightGameTheme,
    )
}
