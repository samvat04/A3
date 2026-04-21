package com.example.sdangol1_a3.ui.viewmodel

import android.util.Log
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

    companion object {
        private const val LOG_TAG = "448.MovieSearchVM"
    }

    private val _stateFlow = MutableStateFlow(MovieSearchState())
    override val stateFlow = _stateFlow.asStateFlow()

    private val _effectFlow = MutableSharedFlow<MovieSearchEffect?>()
    override val effectFlow = _effectFlow.asSharedFlow()

    override fun handleIntent(intent: MovieSearchIntent) {
        when (intent) {
            is MovieSearchIntent.UpdateQuery -> {
                Log.d(LOG_TAG, "Updating query to '${intent.query}'")
                _stateFlow.update {
                    it.copy(query = intent.query)
                }

                if (intent.query.length < 3) {
                    _stateFlow.update {
                        it.copy(
                            results = emptyList(),
                            selectedMovie = null,
                            loading = false
                        )
                    }
                }
            }

            is MovieSearchIntent.Search -> {
                if (intent.query.length < 3) {
                    Log.d(LOG_TAG, "Manual search ignored because query is shorter than 3 chars")
                    _stateFlow.update {
                        it.copy(
                            results = emptyList(),
                            selectedMovie = null,
                            loading = false
                        )
                    }
                    return
                }

                viewModelScope.launch {
                    _stateFlow.update { it.copy(loading = true) }

                    try {
                        Log.d(LOG_TAG, "Manual search for query='${intent.query}'")
                        val results = repo.searchMovies(intent.query)
                        Log.d(LOG_TAG, "Manual search returned ${results.size} results")

                        _stateFlow.update {
                            it.copy(
                                results = results,
                                loading = false
                            )
                        }
                    } catch (e: Exception) {
                        Log.e(LOG_TAG, "Manual search failed", e)
                        _stateFlow.update {
                            it.copy(
                                results = emptyList(),
                                loading = false
                            )
                        }
                        _effectFlow.emit(MovieSearchEffect.SearchFailed)
                    }
                }
            }

            is MovieSearchIntent.SelectMovie -> {
                Log.d(LOG_TAG, "Selected movie: ${intent.movie.title}")
                _stateFlow.update {
                    it.copy(selectedMovie = intent.movie)
                }
            }

            MovieSearchIntent.SaveSelectedMovie -> {
                saveSelectedMovie()
            }
        }
    }

    private fun saveSelectedMovie() {
        val selected = stateFlow.value.selectedMovie ?: return

        viewModelScope.launch {
            try {
                if (repo.alreadyExists(selected.imdbId)) {
                    Log.d(LOG_TAG, "Movie already exists: ${selected.imdbId}")
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

                Log.d(LOG_TAG, "Movie saved: ${selected.title}")
                _effectFlow.emit(MovieSearchEffect.SaveSucceeded)
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Failed to save selected movie", e)
            }
        }
    }
}
