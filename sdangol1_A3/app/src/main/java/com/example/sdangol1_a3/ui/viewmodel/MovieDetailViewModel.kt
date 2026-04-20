package com.example.sdangol1_a3.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdangol1_a3.data.MovieRepo
import com.example.sdangol1_a3.ui.viewmodel.effect.MovieDetailEffect
import com.example.sdangol1_a3.ui.viewmodel.intent.MovieDetailIntent
import com.example.sdangol1_a3.ui.viewmodel.state.MovieDetailState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val repo: MovieRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel(), IViewModelContract<MovieDetailState, MovieDetailIntent, MovieDetailEffect> {

    private val _stateFlow = MutableStateFlow(MovieDetailState())
    override val stateFlow = _stateFlow.asStateFlow()

    private val _effectFlow = MutableSharedFlow<MovieDetailEffect?>()
    override val effectFlow = _effectFlow.asSharedFlow()

    override fun handleIntent(intent: MovieDetailIntent) {
        when (intent) {
            is MovieDetailIntent.LoadMovie -> {
                viewModelScope.launch {
                    _stateFlow.update { it.copy(movie = repo.loadMovie(intent.movieId)) }
                }
            }
            is MovieDetailIntent.DeleteMovie -> {
                viewModelScope.launch {
                    if (repo.deleteMovie(intent.movie)) {
                        _effectFlow.emit(MovieDetailEffect.DeleteSucceeded)
                    } else {
                        _effectFlow.emit(MovieDetailEffect.DeleteFailed)
                    }
                }
            }
            is MovieDetailIntent.FetchCast -> {
                viewModelScope.launch {
                    _stateFlow.update { it.copy(loadingCast = true) }
                    val cast = repo.fetchCast(intent.imdbId)
                    _stateFlow.update { it.copy(cast = cast, loadingCast = false) }
                }
            }
        }
    }
}
