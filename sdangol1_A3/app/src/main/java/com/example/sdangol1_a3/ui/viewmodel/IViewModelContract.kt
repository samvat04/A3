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
    val stateFlow: StateFlow<STATE>
    val effectFlow: SharedFlow<EFFECT?>

    val dispatcher: (INTENT) -> Unit
        get() = { intent -> handleIntent(intent) }

    fun handleIntent(intent: INTENT)

    data class StateDispatchEffect<STATE, INTENT, EFFECT>(
        val state: STATE,
        val dispatcher: (INTENT) -> Unit,
        val effectFlow: SharedFlow<EFFECT?>
    )

    @Composable
    fun use(lifecycleOwner: LifecycleOwner) = StateDispatchEffect(
        state = stateFlow.collectAsStateWithLifecycle(lifecycleOwner).value,
        dispatcher = dispatcher,
        effectFlow = effectFlow
    )
}

@Composable
fun <T> SharedFlow<T>.collectInLaunchedEffect(block: suspend (T) -> Unit) {
    LaunchedEffect(this) {
        collectLatest(block)
    }
}
