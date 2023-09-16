package com.caseyjbrooks.onepagewar.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.caseyjbrooks.onepagewar.ui.LocalInjector
import com.caseyjbrooks.onepagewar.vm.login.LoginContract

internal object LoginUi {
    @Composable
    internal fun Screen() {
        val injector = LocalInjector.current
        val coroutineScope = rememberCoroutineScope()
        val vm = remember(coroutineScope) { injector.loginViewModel(coroutineScope) }

        val state by vm.observeStates().collectAsState()
        Screen(state, vm::trySend)
    }
    @Suppress("UNUSED_PARAMETER")
    @Composable
    internal fun Screen(
        state: LoginContract.State,
        postInput: (LoginContract.Inputs) -> Unit,
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                var password by remember { mutableStateOf(TextFieldValue("")) }

                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Enter password") },
                )

                Button(
                    onClick = {
                        postInput(LoginContract.Inputs.TryLogin(password.text))
                    },
                    enabled = password.text.isNotEmpty()
                ) {
                    Text("Log In")
                }
            }
        }
    }
}

