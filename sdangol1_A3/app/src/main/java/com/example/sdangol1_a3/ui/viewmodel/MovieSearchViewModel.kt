package com.example.sdangol1_a3.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.data.MovieRepo
import com.example.sdangol1_a3.ui.viewmodel.effect.MovieSearchEffect
import com.example.sdangol1_a3.ui.viewmodel.intent.MovieSearchIntent
import com.example.sdangol1_a3.ui.viewmodel.state.MovieSearchState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieSearchViewModel(
    private val repo: MovieRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel(), IViewModelContract<MovieSearchState, MovieSearchIntent, MovieSearchEffect> {

    private val _stateFlow = MutableStateFlow(MovieSearchState())
    override val stateFlow = _stateFlow.asStateFlow()

    private val _effectFlow = MutableSharedFlow<MovieSearchEffect?>()
    override val effectFlow = _effectFlow.asSharedFlow()

    override fun handleIntent(intent: MovieSearchIntent) {
        when (intent) {
            is MovieSearchIntent.UpdateQuery -> {
                _stateFlow.update { it.copy(query = intent.query) }
            }
            is MovieSearchIntent.Search -> {
                viewModelScope.launch {
                    _stateFlow.update { it.copy(loading = true) }
                    val results = repo.searchMovies(intent.query)
                    _stateFlow.update { it.copy(results = results, loading = false) }
                }
            }
            is MovieSearchIntent.SelectMovie -> {
                _stateFlow.update { it.copy(selectedMovie = intent.movie) }
            }
            MovieSearchIntent.SaveSelectedMovie -> {
                saveSelectedMovie()
            }
        }
    }

    private fun saveSelectedMovie() {
        val selected = stateFlow.value.selectedMovie ?: return
        viewModelScope.launch {
            if (repo.alreadyExists(selected.imdbId)) {
                _effectFlow.emit(MovieSearchEffect.MovieAlreadyExists)
                return@launch
            }

            repo.addMovie(
                Movie(
                    imdbId = selected.imdbId,
                    title = selected.title,
                    description = selected.description,
                    year = selected.year,
                    imageUrl = selected.imageUrl
                )
            )
            _effectFlow.emit(MovieSearchEffect.SaveSucceeded)
        }
    }
}
