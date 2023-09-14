package com.copperleaf.onepagewar.vm.game.models

import kotlinx.serialization.Serializable

@Serializable
public data class ArenaData(
    public val values: List<List<Int?>> = emptyList(),
    public val powerUses: List<Boolean> = emptyList(),
    public val specialMark: String? = null,
)
