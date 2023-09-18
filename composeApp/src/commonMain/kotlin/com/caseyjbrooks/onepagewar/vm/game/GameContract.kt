package com.caseyjbrooks.onepagewar.vm.game

import com.caseyjbrooks.onepagewar.vm.game.models.ArenaData
import com.caseyjbrooks.onepagewar.vm.game.models.CurrentMove
import com.caseyjbrooks.onepagewar.vm.game.models.Player
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlinx.serialization.json.Json
import kotlin.random.Random

public object GameContract {
    @Serializable
    public data class State(
        val gameId: Int = Random.nextInt(1000, 9999),

        val diceIsRolling: Boolean = false,
        val diceValues: List<Int> = emptyList(),

        val currentMove: CurrentMove? = null,

        val players: List<Player> = listOf(
            Player("Player 1", bot = false),
            Player("Player 2", bot = false),
        ),
        val arenaData: Map<String, ArenaData> = emptyMap(),
    ) {
        @Transient
        private val random: Random = Random(gameId)

        init {
            // recreate the random instance on each state update. When we do, use it as many times as we currently have
            // dice rolls, to ensure other users can set the same seed and get back to the same value. This effectively
            // locks the internal state of the Random instance to the same value based on the state of
            // [gameId] and [diceValues].
            repeat(diceValues.size) {
                getNextDiceValue()
            }
        }

        internal fun getNextDiceValue(): Int {
            return random.nextInt(1, 6 + 1)
        }

        override fun toString(): String {
            return Json.encodeToString(GameContract.State.serializer(), this)
        }
    }

    @Serializable
    public sealed interface Inputs {
        // ----------- //
        // Manage Game //
        // ----------- //

        @Serializable
        public data object NewGame : Inputs

        @Serializable
        public data class UpdateGameSettings(val themeId: String, val gameId: Int) : Inputs

        @Serializable
        public data class AddPlayer(val playerName: String, val bot: Boolean) : Inputs

        @Serializable
        public data class RemovePlayer(val playerName: String) : Inputs

        @Serializable
        public data class UpdatePlayerDetails(
            val oldPlayerName: String,
            val newPlayerName: String,
            val bot: Boolean,
        ) : Inputs

        // ----------- //
        // Manage Dice //
        // ----------- //

        @Serializable
        public data object RollDice : Inputs

        @Serializable
        public data object ClearDiceRolls : Inputs

        @Serializable
        public data class DiceValueUpdated(val value: Int) : Inputs

        @Serializable
        public data class SetCurrentMove(
            public val playerName: String,
            public val arenaId: String,
        ) : Inputs

        // --------- //
        // Undo/Redo //
        // --------- //

        @Serializable
        public data object Undo : Inputs

        @Serializable
        public data object Redo : Inputs

        // ----------------- //
        // Update Game State //
        // ----------------- //

        @Serializable
        public data class SetArenaValue(
            val arenaId: String,
            val playerName: String,
            val valueIndex: Int,
            val value: Int?
        ) : Inputs

        @Serializable
        public data class SetArenaPowerUsed(
            val arenaId: String,
            val index: Int,
            val used: Boolean,
        ) : Inputs

        @Serializable
        public data class MarkArenaWithThemeSpecial(
            val arenaId: String,
        ) : Inputs
    }

    @Serializable
    public sealed interface Events {
        @Serializable
        public data class NavigateTo(val route: String) : Events
    }
}
