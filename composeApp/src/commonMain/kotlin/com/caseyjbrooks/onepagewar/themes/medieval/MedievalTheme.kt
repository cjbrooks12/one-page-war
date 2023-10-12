package com.caseyjbrooks.onepagewar.themes.medieval

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import com.caseyjbrooks.onepagewar.resources.MR
import com.caseyjbrooks.onepagewar.themes.GameThemes.EMPTY_VALUE
import com.caseyjbrooks.onepagewar.themes.GameThemes.REMOVE_VALUE
import com.caseyjbrooks.onepagewar.themes.GameThemes.WILD_VALUE
import com.caseyjbrooks.onepagewar.vm.game.models.Arena
import com.caseyjbrooks.onepagewar.vm.game.models.Theme

public object MedievalTheme : Theme(
    id = "medieval",
    name = MR.strings.medieval_name,
    enabled = true,
    specialMark = "A",
    powerShape = RectangleShape,
    handValues = MR.strings.medieval_hand_values,
    playerDiceValues = listOf(
        1, 2, 3, 4, 5,
        6, 7, 8, WILD_VALUE, REMOVE_VALUE,
    ),
    botDiceValues = listOf(
        1, 2, 3, 4, 5,
        6, 7, WILD_VALUE, EMPTY_VALUE, REMOVE_VALUE,
    ),
    arenas = listOf(
        Arena(
            id = "infantry",

            name = MR.strings.medieval_arena1_name,
            color = Color(0xFF68BF66),
            powerTitle = MR.strings.medieval_arena1_power_title,
            powerDescription = MR.strings.medieval_arena1_power_description,

            numberOfValues = 4,
            numberOfPowerUses = 4,
        ),
        Arena(
            id = "archery",

            name = MR.strings.medieval_arena2_name,
            color = Color(0xFF9FDBE7),
            powerTitle = MR.strings.medieval_arena2_power_title,
            powerDescription = MR.strings.medieval_arena2_power_description,

            numberOfValues = 4,
            numberOfPowerUses = 2,
        ),
        Arena(
            id = "cavalry",

            name = MR.strings.medieval_arena3_name,
            color = Color(0xFF79C9AD),
            powerTitle = MR.strings.medieval_arena3_power_title,
            powerDescription = MR.strings.medieval_arena3_power_description,

            numberOfValues = 3,
            numberOfPowerUses = 3,
        ),
        Arena(
            id = "court",

            name = MR.strings.medieval_arena4_name,
            color = Color(0xFFD56E50),
            powerTitle = MR.strings.medieval_arena4_power_title,
            powerDescription = MR.strings.medieval_arena4_power_description,

            numberOfValues = 4,
            numberOfPowerUses = 1,
        ),
        Arena(
            id = "siegecraft",

            name = MR.strings.medieval_arena5_name,
            color = Color(0xFFF8D508),
            powerTitle = MR.strings.medieval_arena5_power_title,
            powerDescription = MR.strings.medieval_arena5_power_description,

            numberOfValues = 4,
            numberOfPowerUses = 2,
        )
    )
)
