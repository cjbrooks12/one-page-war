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
        // -------------------------------------------------------------------------------------------------------------
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
        public val modern_arena3_power_title: StringResource = StringResource("Redeployment")
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
        // -------------------------------------------------------------------------------------------------------------

        public val medieval_name: StringResource = StringResource("Medieval")

        public val medieval_arena1_name: StringResource = StringResource("Infantry")
        public val medieval_arena1_power_title: StringResource = StringResource("Battle Formations")
        public val medieval_arena1_power_description: StringResource =
            StringResource("Adjust the dice up or down yb one (or turn 1 to 6).")

        public val medieval_arena2_name: StringResource = StringResource("Archery")
        public val medieval_arena2_power_title: StringResource = StringResource("Armor Piercing")
        public val medieval_arena2_power_description: StringResource =
            StringResource("Replace a number with a star (wild number).")

        public val medieval_arena3_name: StringResource = StringResource("Cavalry")
        public val medieval_arena3_power_title: StringResource = StringResource("Surprise Charge")
        public val medieval_arena3_power_description: StringResource =
            StringResource("Move a number to an empty square in any arena")

        public val medieval_arena4_name: StringResource = StringResource("Court")
        public val medieval_arena4_power_title: StringResource = StringResource("alliance")
        public val medieval_arena4_power_description: StringResource =
            StringResource("Mark an arena with an `A`. Winning that counts as winning two.")

        public val medieval_arena5_name: StringResource = StringResource("Siegecraft")
        public val medieval_arena5_power_title: StringResource = StringResource("War Machines")
        public val medieval_arena5_power_description: StringResource =
            StringResource("Increase any numbers in an arena by 1.")

        public val medieval_hand_values: List<StringResource> = listOf(
            StringResource("Four of a kind"),
            StringResource("Three of a kind"),
            StringResource("Straight"),
            StringResource("Two Pairs"),
            StringResource("One Pair"),
            StringResource("Nothing"),
        )

        // Strings for water fight variant
        // -------------------------------------------------------------------------------------------------------------

        public val water_fight_name: StringResource = StringResource("Water Fight")

        public val water_fight_arena1_name: StringResource = StringResource("Treehouse")
        public val water_fight_arena1_power_title: StringResource = StringResource("Sniper Spot!")
        public val water_fight_arena1_power_description: StringResource =
            StringResource("Write any number you want instead of using the dice.")

        public val water_fight_arena2_name: StringResource = StringResource("Balcony")
        public val water_fight_arena2_power_title: StringResource = StringResource("Bombardment!")
        public val water_fight_arena2_power_description: StringResource =
            StringResource("Replace the number you have most of in an arena with current dice number.")

        public val water_fight_arena3_name: StringResource = StringResource("Pool")
        public val water_fight_arena3_power_title: StringResource = StringResource("Ambush!")
        public val water_fight_arena3_power_description: StringResource =
            StringResource("Mark one arena with an `L`. In the marked arena, lowest scoring wins.")

        public val water_fight_arena4_name: StringResource = StringResource("Water Tap")
        public val water_fight_arena4_power_title: StringResource = StringResource("Reload!")
        public val water_fight_arena4_power_description: StringResource =
            StringResource("Use another unlocked arena power that has already been ticked off.")

        public val water_fight_arena5_name: StringResource = StringResource("Fence")
        public val water_fight_arena5_power_title: StringResource = StringResource("Take Cover!")
        public val water_fight_arena5_power_description: StringResource =
            StringResource("Cope another number in the same arena instead of using the dice.")

        public val water_fight_hand_values: List<StringResource> = listOf(
            StringResource("Three of a kind"),
            StringResource("Straight"),
            StringResource("One Pair"),
            StringResource("Nothing"),
        )
        
        // Galaxy Wars
        // -------------------------------------------------------------------------------------------------------------

        public val galaxy_wars_name: StringResource = StringResource("Galaxy Wars")

        public val galaxy_wars_arena1_name: StringResource = StringResource("Intergalactic")
        public val galaxy_wars_arena1_power_title: StringResource = StringResource("Superior Armada")
        public val galaxy_wars_arena1_power_description: StringResource =
            StringResource("Mark one arena with an 'N'. In the marked arena, all combos count as 'Nothing'")

        public val galaxy_wars_arena2_name: StringResource = StringResource("Planetary")
        public val galaxy_wars_arena2_power_title: StringResource = StringResource("Mech Strike")
        public val galaxy_wars_arena2_power_description: StringResource =
            StringResource("Instead of using current dice number, copy the lowest number in the arena you write into.")

        public val galaxy_wars_arena3_name: StringResource = StringResource("Hyper Dimensional")
        public val galaxy_wars_arena3_power_title: StringResource = StringResource("Warm Portal")
        public val galaxy_wars_arena3_power_description: StringResource =
            StringResource("Swap any two numbers.")

        public val galaxy_wars_arena4_name: StringResource = StringResource("Extra Terrestrial")
        public val galaxy_wars_arena4_power_title: StringResource = StringResource("Alien Technology")
        public val galaxy_wars_arena4_power_description: StringResource =
            StringResource("Increase one number by one and reduce another by one.")

        public val galaxy_wars_arena5_name: StringResource = StringResource("Paranormal")
        public val galaxy_wars_arena5_power_title: StringResource = StringResource("Psychic Powers")
        public val galaxy_wars_arena5_power_description: StringResource =
            StringResource("The next time the dice is to be rolled, keep the same number instead of rolling.")

        public val galaxy_wars_hand_values: List<StringResource> = listOf(
            StringResource("Five of a kind"),
            StringResource("Four of a kind"),
            StringResource("Full House"),
            StringResource("Three of a kind"),
            StringResource("Straight"),
            StringResource("Two Pairs"),
            StringResource("One Pair"),
            StringResource("Nothing"),
        )
    }
}
