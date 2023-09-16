package com.caseyjbrooks.onepagewar.ui.notfound

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.caseyjbrooks.onepagewar.resources.MR
import com.caseyjbrooks.onepagewar.ui.LocalInjector
import com.caseyjbrooks.onepagewar.vm.notfound.NotFoundContract

internal object NotFoundUi {
    @Composable
    internal fun Screen() {
        val injector = LocalInjector.current
        val coroutineScope = rememberCoroutineScope()
        val vm = remember(coroutineScope) { injector.notFoundViewModel(coroutineScope) }

        val state by vm.observeStates().collectAsState()
        Screen(state, vm::trySend)
    }

    @Suppress("UNUSED_PARAMETER")
    @Composable
    internal fun Screen(
        state: NotFoundContract.State,
        postInput: (NotFoundContract.Inputs) -> Unit,
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.height(64.dp).fillMaxWidth(),
            ) {
                Text(MR.strings.not_found(), modifier = Modifier.align(Alignment.Center))
                IconButton(
                    onClick = { postInput(NotFoundContract.Inputs.GoHome) },
                    modifier = Modifier.align(Alignment.CenterStart),
                ) {
                    Icon(Icons.Default.Home, MR.strings.go_home())
                }
            }
            Divider()
            Box(
                modifier = Modifier.fillMaxWidth().weight(1f)
            ) {
                Text(MR.strings.are_you_lost())

                Button(
                    onClick = { postInput(NotFoundContract.Inputs.GoHome) },
                    modifier = Modifier.align(Alignment.CenterStart),
                ) {
                    Text(MR.strings.go_home())
                }
            }
        }
    }
}

