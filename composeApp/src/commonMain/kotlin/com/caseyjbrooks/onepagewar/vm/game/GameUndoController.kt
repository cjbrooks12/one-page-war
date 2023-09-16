package com.caseyjbrooks.onepagewar.vm.game

import com.copperleaf.ballast.undo.UndoController

internal typealias GameUndoController = UndoController<
        GameContract.Inputs,
        GameContract.Events,
        GameContract.State>
