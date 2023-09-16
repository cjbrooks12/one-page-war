package com.caseyjbrooks.onepagewar.vm.game

import com.copperleaf.ballast.BallastViewModel

internal typealias GameViewModel = BallastViewModel<
        GameContract.Inputs,
        GameContract.Events,
        GameContract.State>
