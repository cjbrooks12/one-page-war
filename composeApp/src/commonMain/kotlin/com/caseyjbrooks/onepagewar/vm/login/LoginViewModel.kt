package com.caseyjbrooks.onepagewar.vm.login

import com.copperleaf.ballast.BallastViewModel

internal typealias LoginViewModel = BallastViewModel<
        LoginContract.Inputs,
        LoginContract.Events,
        LoginContract.State>
