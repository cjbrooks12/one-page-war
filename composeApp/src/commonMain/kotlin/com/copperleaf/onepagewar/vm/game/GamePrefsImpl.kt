package com.copperleaf.onepagewar.vm.game

import com.russhwolf.settings.Settings
import kotlinx.serialization.json.Json

internal class GamePrefsImpl(
    private val settings: Settings,
    private val json: Json = Json,
) : GamePrefs {
    override var gameState: GameContract.State
        get() {
            return runCatching {
                json.decodeFromString(GameContract.State.serializer(), settings.getString("gameState", ""))
            }.getOrElse { GameContract.State() }
        }
        set(value) {
            settings.putString("gameState", json.encodeToString(GameContract.State.serializer(), value))
        }

}
