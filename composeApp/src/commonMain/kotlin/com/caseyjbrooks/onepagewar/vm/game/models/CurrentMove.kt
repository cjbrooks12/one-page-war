package com.caseyjbrooks.onepagewar.vm.game.models

import kotlinx.serialization.Serializable

@Serializable
public data class CurrentMove(
    public val playerName: String,
    public val arenaId: String,
    public val diceIndex: Int,
)
