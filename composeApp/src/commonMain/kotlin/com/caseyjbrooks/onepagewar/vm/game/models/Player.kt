package com.caseyjbrooks.onepagewar.vm.game.models

import kotlinx.serialization.Serializable

@Serializable
public data class Player(
    public val name: String,
    public val bot: Boolean,
)
