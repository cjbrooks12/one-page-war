package com.copperleaf.onepagewar.themes.modern

import androidx.compose.ui.graphics.Color
import com.copperleaf.onepagewar.resources.MR
import com.copperleaf.onepagewar.vm.game.models.Arena
import com.copperleaf.onepagewar.vm.game.models.Theme

internal val ModernGameTheme = Theme(
    id = "modern",
    name = MR.strings.modern_name,
    enabled = true,
    specialMark = "S",
    arenas = listOf(
        Arena(
            id = "land",

            name = MR.strings.modern_arena1_name,
            color = Color(0xFF68BF66),
            powerTitle = MR.strings.modern_arena1_power_title,
            powerDescription = MR.strings.modern_arena1_power_description,

            numberOfValues = 3,
            numberOfPowerUses = 3,
        ),
        Arena(
            id = "air",

            name = MR.strings.modern_arena2_name,
            color = Color(0xFF9FDBE7),
            powerTitle = MR.strings.modern_arena2_power_title,
            powerDescription = MR.strings.modern_arena2_power_description,

            numberOfValues = 4,
            numberOfPowerUses = 3,
        ),
        Arena(
            id = "sea",

            name = MR.strings.modern_arena3_name,
            color = Color(0xFF79C9AD),
            powerTitle = MR.strings.modern_arena3_power_title,
            powerDescription = MR.strings.modern_arena3_power_description,

            numberOfValues = 4,
            numberOfPowerUses = 4,
        ),
        Arena(
            id = "politics",

            name = MR.strings.modern_arena4_name,
            color = Color(0xFFD56E50),
            powerTitle = MR.strings.modern_arena4_power_title,
            powerDescription = MR.strings.modern_arena4_power_description,

            numberOfValues = 5,
            numberOfPowerUses = 1,
        ),
        Arena(
            id = "espionage",

            name = MR.strings.modern_arena5_name,
            color = Color(0xFFF8D508),
            powerTitle = MR.strings.modern_arena5_power_title,
            powerDescription = MR.strings.modern_arena5_power_description,

            numberOfValues = 3,
            numberOfPowerUses = 2,
        )
    )
)
