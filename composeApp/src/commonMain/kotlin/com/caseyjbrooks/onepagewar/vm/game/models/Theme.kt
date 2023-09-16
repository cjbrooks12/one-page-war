package com.caseyjbrooks.onepagewar.vm.game.models

import com.caseyjbrooks.onepagewar.resources.StringResource

public data class Theme(
    public val id: String,
    public val name: StringResource,
    public val enabled: Boolean,
    public val specialMark: String?,
    public val arenas: List<Arena>,
)
