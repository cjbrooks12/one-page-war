package com.copperleaf.onepagewar.vm.game

import com.copperleaf.ballast.undo.UndoController

internal typealias GameUndoController = UndoController<
        GameContract.Inputs,
        GameContract.Events,
        GameContract.State>
