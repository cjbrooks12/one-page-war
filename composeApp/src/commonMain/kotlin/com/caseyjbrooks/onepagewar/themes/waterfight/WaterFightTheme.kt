package com.caseyjbrooks.onepagewar.themes.waterfight

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.graphics.Color
import com.caseyjbrooks.onepagewar.resources.MR
import com.caseyjbrooks.onepagewar.themes.GameThemes.EMPTY_VALUE
import com.caseyjbrooks.onepagewar.themes.GameThemes.REMOVE_VALUE
import com.caseyjbrooks.onepagewar.themes.GameThemes.WILD_VALUE
import com.caseyjbrooks.onepagewar.vm.game.models.Arena
import com.caseyjbrooks.onepagewar.vm.game.models.Theme

public object WaterFightTheme : Theme(
    id = "water-fight",
    name = MR.strings.water_fight_name,
    enabled = true,
    specialMark = "L",
    powerShape = CircleShape,
    handValues = MR.strings.water_fight_hand_values,
    playerDiceValues = listOf(
        1, 2, 3, 4, 5,
        6, EMPTY_VALUE, EMPTY_VALUE, EMPTY_VALUE, REMOVE_VALUE,
    ),
    botDiceValues = listOf(
        1, 2, 3, 4, 5,
        6, 7, WILD_VALUE, EMPTY_VALUE, REMOVE_VALUE,
    ),
    arenas = listOf(
        Arena(
            id = "treehouse",

            name = MR.strings.water_fight_arena1_name,
            color = Color(0xFF68BF66),
            powerTitle = MR.strings.water_fight_arena1_power_title,
            powerDescription = MR.strings.water_fight_arena1_power_description,

            numberOfValues = 3,
            numberOfPowerUses = 1,
        ),
        Arena(
            id = "balcony",

            name = MR.strings.water_fight_arena2_name,
            color = Color(0xFF9FDBE7),
            powerTitle = MR.strings.water_fight_arena2_power_title,
            powerDescription = MR.strings.water_fight_arena2_power_description,

            numberOfValues = 3,
            numberOfPowerUses = 1,
        ),
        Arena(
            id = "pool",

            name = MR.strings.water_fight_arena3_name,
            color = Color(0xFF79C9AD),
            powerTitle = MR.strings.water_fight_arena3_power_title,
            powerDescription = MR.strings.water_fight_arena3_power_description,

            numberOfValues = 3,
            numberOfPowerUses = 1,
        ),
        Arena(
            id = "water-tap",

            name = MR.strings.water_fight_arena4_name,
            color = Color(0xFFD56E50),
            powerTitle = MR.strings.water_fight_arena4_power_title,
            powerDescription = MR.strings.water_fight_arena4_power_description,

            numberOfValues = 3,
            numberOfPowerUses = 1,
        ),
        Arena(
            id = "fence",

            name = MR.strings.water_fight_arena5_name,
            color = Color(0xFFF8D508),
            powerTitle = MR.strings.water_fight_arena5_power_title,
            powerDescription = MR.strings.water_fight_arena5_power_description,

            numberOfValues = 3,
            numberOfPowerUses = 2,
        )
    )
)
