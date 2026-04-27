package com.example.sdangol1_a3.ui.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.sdangol1_a3.ui.viewmodel.effect.MovieEffect
import com.example.sdangol1_a3.ui.viewmodel.intent.MovieIntent
import com.example.sdangol1_a3.ui.viewmodel.state.MovieState
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest

interface IViewModelContract<STATE : MovieState, INTENT : MovieIntent, EFFECT : MovieEffect> {
    // Continuous UI state exposed to the composable layer
    val stateFlow: StateFlow<STATE>

    // One time events such as snackbars or navigation effects
    val effectFlow: SharedFlow<EFFECT?>

    // Gives screens a consistent way to send user intents into the ViewModel
    val dispatcher: (INTENT) -> Unit
        get() = { intent -> handleIntent(intent) }

    // Each ViewModel implements how incoming intents should be handled
    fun handleIntent(intent: INTENT)

    data class StateDispatchEffect<STATE, INTENT, EFFECT>(
        val state: STATE,
        val dispatcher: (INTENT) -> Unit,
        val effectFlow: SharedFlow<EFFECT?>
    )

    @Composable
    fun use(lifecycleOwner: LifecycleOwner) = StateDispatchEffect(
        // Lifecycle aware collection
        state = stateFlow.collectAsStateWithLifecycle(lifecycleOwner).value,
        dispatcher = dispatcher,
        effectFlow = effectFlow
    )
}

@Composable
fun <T> SharedFlow<T>.collectInLaunchedEffect(block: suspend (T) -> Unit) {
    LaunchedEffect(this) {
        // collectLatest ensures only the newest effect is handled
        collectLatest(block)
    }
}
