package com.caseyjbrooks.onepagewar.vm.game.models

import androidx.compose.ui.graphics.Color
import com.caseyjbrooks.onepagewar.resources.StringResource

public data class Arena(
    public val id: String,

    public val name: StringResource,
    public val color: Color,
    public val powerTitle: StringResource,
    public val powerDescription: StringResource,

    public val numberOfValues: Int,
    public val numberOfPowerUses: Int,
)
