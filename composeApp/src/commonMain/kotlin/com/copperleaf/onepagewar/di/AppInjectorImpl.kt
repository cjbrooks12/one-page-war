package com.copperleaf.onepagewar.di

import com.copperleaf.ballast.BallastViewModelConfiguration
import com.copperleaf.ballast.build
import com.copperleaf.ballast.core.BasicViewModel
import com.copperleaf.ballast.core.BootstrapInterceptor
import com.copperleaf.ballast.core.FifoInputStrategy
import com.copperleaf.ballast.core.LoggingInterceptor
import com.copperleaf.ballast.core.PrintlnLogger
import com.copperleaf.ballast.debugger.BallastDebuggerClientConnection
import com.copperleaf.ballast.debugger.BallastDebuggerInterceptor
import com.copperleaf.ballast.debugger.DebuggerAdapter
import com.copperleaf.ballast.debugger.JsonDebuggerAdapter
import com.copperleaf.ballast.debugger.ToStringDebuggerAdapter
import com.copperleaf.ballast.eventHandler
import com.copperleaf.ballast.navigation.routing.RoutingTable
import com.copperleaf.ballast.navigation.routing.fromEnum
import com.copperleaf.ballast.navigation.vm.Router
import com.copperleaf.ballast.navigation.vm.withRouter
import com.copperleaf.ballast.plusAssign
import com.copperleaf.ballast.savedstate.BallastSavedStateInterceptor
import com.copperleaf.ballast.undo.BallastUndoInterceptor
import com.copperleaf.ballast.undo.state.StateBasedUndoController
import com.copperleaf.ballast.undo.state.withStateBasedUndoController
import com.copperleaf.ballast.withViewModel
import com.copperleaf.onepagewar.AppScreen
import com.copperleaf.onepagewar.NativeUiUtils
import com.copperleaf.onepagewar.NativeUiUtils.debug
import com.copperleaf.onepagewar.resources.MR
import com.copperleaf.onepagewar.vm.game.GameContract
import com.copperleaf.onepagewar.vm.game.GameEventHandler
import com.copperleaf.onepagewar.vm.game.GameInputHandler
import com.copperleaf.onepagewar.vm.game.GamePrefsImpl
import com.copperleaf.onepagewar.vm.game.GameSavedStateAdapter
import com.copperleaf.onepagewar.vm.game.GameUndoController
import com.copperleaf.onepagewar.vm.game.GameViewModel
import com.copperleaf.onepagewar.vm.game.models.Theme
import com.copperleaf.onepagewar.vm.login.LoginContract
import com.copperleaf.onepagewar.vm.login.LoginEventHandler
import com.copperleaf.onepagewar.vm.login.LoginInputHandler
import com.copperleaf.onepagewar.vm.login.LoginPrefsImpl
import com.copperleaf.onepagewar.vm.login.LoginViewModel
import com.copperleaf.onepagewar.vm.notfound.NotFoundContract
import com.copperleaf.onepagewar.vm.notfound.NotFoundEventHandler
import com.copperleaf.onepagewar.vm.notfound.NotFoundInputHandler
import com.copperleaf.onepagewar.vm.notfound.NotFoundViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.sample
import kotlin.time.Duration.Companion.milliseconds

internal class AppInjectorImpl(
    private val applicationCoroutineScope: CoroutineScope,
) : AppInjector {
    override val router: Router<AppScreen> by lazy {
        BasicViewModel(
            config = BallastViewModelConfiguration.Builder()
                .commonConfig(
                    logging = true
                )
                .withRouter(RoutingTable.fromEnum(AppScreen.entries.toTypedArray()), AppScreen.LogIn)
                .commonTypedConfig(
                    debugging = true
                )
                .build(),
            eventHandler = eventHandler { },
            coroutineScope = applicationCoroutineScope
        )
    }

    private val debuggerClientConnection: BallastDebuggerClientConnection<*> by lazy {
        NativeUiUtils
            .createDebuggerConnection(applicationCoroutineScope)
            .also { it.connect(PrintlnLogger("Debugger")) }
    }

    private val loginPrefs by lazy {
        LoginPrefsImpl(
            NativeUiUtils.createSettings("login")
        )
    }

// Login
// ---------------------------------------------------------------------------------------------------------------------

    override fun notFoundViewModel(coroutineScope: CoroutineScope): NotFoundViewModel {
        return BasicViewModel(
            coroutineScope = coroutineScope,
            config = BallastViewModelConfiguration.Builder()
                .commonConfig(
                    logging = true
                )
                .withViewModel(
                    initialState = NotFoundContract.State(),
                    inputHandler = NotFoundInputHandler(),
                    name = "NotFound"
                )
                .commonTypedConfig(
                    debugging = true,
                )
                .build(),
            eventHandler = NotFoundEventHandler(router),
        )
    }

    override fun loginViewModel(coroutineScope: CoroutineScope): LoginViewModel {
        return BasicViewModel(
            coroutineScope = coroutineScope,
            config = BallastViewModelConfiguration.Builder()
                .commonConfig(
                    logging = true
                )
                .withViewModel(
                    initialState = LoginContract.State(),
                    inputHandler = LoginInputHandler(
                        hardcodedPassword = MR.strings.hardcodedPassword,
                        prefs = loginPrefs,
                    ),
                    name = "Login"
                )
                .commonTypedConfig(
                    debugging = true,
                )
                .apply {
                    this += BootstrapInterceptor {
                        LoginContract.Inputs.Initialize
                    }
                }
                .build(),
            eventHandler = LoginEventHandler(router),
        )
    }

    override fun gameViewModel(
        coroutineScope: CoroutineScope,
        theme: Theme,
        initialGameId: Int?
    ): Pair<GameViewModel, GameUndoController> {
        loginPrefs.selectedThemeId = theme.id

        val undoController = newModernGameUndoController()

        val vm = BasicViewModel(
            coroutineScope = coroutineScope,
            config = BallastViewModelConfiguration.Builder()
                .commonConfig(
                    logging = true
                )
                .withViewModel(
                    initialState = GameContract.State(),
                    inputHandler = GameInputHandler(
                        theme = theme,
                    ),
                    name = "One Page War | ${theme.id}"
                )
                .apply {
                    this += BallastUndoInterceptor(
                        controller = undoController,
                    )
                    this += BallastSavedStateInterceptor(
                        adapter = GameSavedStateAdapter(
                            prefs = GamePrefsImpl(
                                NativeUiUtils.createSettings(theme.id)
                            ),
                            initialGameId = initialGameId,
                        ),
                    )
                }
                .commonTypedConfig(
                    debugging = true,
                    adapter = JsonDebuggerAdapter(
                        inputsSerializer = GameContract.Inputs.serializer(),
                        eventsSerializer = GameContract.Events.serializer(),
                        stateSerializer = GameContract.State.serializer(),
                    )
                )
                .build(),
            eventHandler = GameEventHandler(router),
        )

        return vm to undoController
    }

// Common configurations
// ---------------------------------------------------------------------------------------------------------------------

    fun BallastViewModelConfiguration.Builder.commonConfig(
        logging: Boolean,
    ): BallastViewModelConfiguration.Builder {
        return this
            .apply {
                inputStrategy = FifoInputStrategy()

                if (debug && logging) {
                    this += LoggingInterceptor(logDebug = false, logInfo = true, logError = true)
                    logger = ::PrintlnLogger
                }
            }
    }

    fun <Inputs : Any, Events : Any, State : Any> BallastViewModelConfiguration.TypedBuilder<Inputs, Events, State>.commonTypedConfig(
        debugging: Boolean,
        adapter: DebuggerAdapter<Inputs, Events, State> = ToStringDebuggerAdapter(),
    ): BallastViewModelConfiguration.TypedBuilder<Inputs, Events, State> {
        return this
            .apply {
                if (debug && debugging) {
                    this += BallastDebuggerInterceptor(
                        debuggerClientConnection,
                        adapter = adapter
                    )
                }
            }
    }

    @OptIn(FlowPreview::class)
    private fun newModernGameUndoController(): GameUndoController {
        return StateBasedUndoController(
            config = BallastViewModelConfiguration.Builder()
                .commonConfig(logging = false)
                .withStateBasedUndoController<
                        GameContract.Inputs,
                        GameContract.Events,
                        GameContract.State>(
                    bufferStates = { incomingFlow ->
                        incomingFlow
                            .filter { !it.diceIsRolling }
                            .sample(100.milliseconds)
                    },
                    historyDepth = 100,
                )
                .commonTypedConfig(debugging = true)
                .build(),
        )
    }
}

