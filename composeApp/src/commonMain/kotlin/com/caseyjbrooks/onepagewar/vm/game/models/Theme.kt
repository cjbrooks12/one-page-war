package com.caseyjbrooks.onepagewar.vm.game.models

import androidx.compose.ui.graphics.Shape
import com.caseyjbrooks.onepagewar.resources.StringResource

public abstract class Theme(
    public val id: String,
    public val name: StringResource,
    public val enabled: Boolean,
    public val specialMark: String?,
    public val powerShape: Shape,
    public val playerDiceValues: List<Int?>,
    public val botDiceValues: List<Int?>,
    public val handValues: List<StringResource>,
    public val arenas: List<Arena>,
)
