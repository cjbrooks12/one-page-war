package com.caseyjbrooks.onepagewar.resources

public object MR {

    public object strings {
        public val app_name: StringResource = StringResource("One Page War")

        public val not_found: StringResource = StringResource("Not Found")
        public val are_you_lost: StringResource = StringResource("Are You Lost?")
        public val go_home: StringResource = StringResource("Go Home")

        public val general_settings: StringResource = StringResource("General Settings")

        public val hardcodedPassword: StringResource = StringResource("CopperLeaf")
        public val coming_soon: StringResource = StringResource("coming soon")

        public val human: StringResource = StringResource("Human")
        public val bot: StringResource = StringResource("Bot")

        // Strings for original modern warfare variant
        // --------------------------------------------------------------------------------------------------------------
        public val modern_name: StringResource = StringResource("Modern")

        public val modern_arena1_name: StringResource = StringResource("Land")
        public val modern_arena1_power_title: StringResource = StringResource("Armored Support")
        public val modern_arena1_power_description: StringResource =
            StringResource("Adjust one number up or down by one.")

        public val modern_arena2_name: StringResource = StringResource("Air")
        public val modern_arena2_power_title: StringResource = StringResource("Air Raid")
        public val modern_arena2_power_description: StringResource =
            StringResource("After writing the current number, also use it to replace another number.")

        public val modern_arena3_name: StringResource = StringResource("Sea")
        public val modern_arena3_power_title: StringResource = StringResource("Reployment")
        public val modern_arena3_power_description: StringResource =
            StringResource("Swap two numbers in adjacent arenas.")

        public val modern_arena4_name: StringResource = StringResource("Politics")
        public val modern_arena4_power_title: StringResource = StringResource("Allies")
        public val modern_arena4_power_description: StringResource =
            StringResource("Mark one arena with an 'S'. In the marked arena, straight beats everything.")

        public val modern_arena5_name: StringResource = StringResource("Espionage")
        public val modern_arena5_power_title: StringResource = StringResource("Stolen Intel")
        public val modern_arena5_power_description: StringResource =
            StringResource("Copy an enemy number in same arena, instead of using the dice.")

        public val modern_hand_values: List<StringResource> = listOf(
            StringResource("Five of a kind"),
            StringResource("Four of a kind"),
            StringResource("Three of a kind"),
            StringResource("Straight"),
            StringResource("Two Pairs"),
            StringResource("One Pair"),
            StringResource("Nothing"),
        )

        // Strings for medieval warfare variant
        // --------------------------------------------------------------------------------------------------------------

        public val medieval_name: StringResource = StringResource("Medieval")

        // Strings for water fight variant
        // --------------------------------------------------------------------------------------------------------------

        public val water_fight_name: StringResource = StringResource("Water Fight")

    }
}
