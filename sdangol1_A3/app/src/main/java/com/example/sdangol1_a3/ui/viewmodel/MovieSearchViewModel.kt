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
                val query = intent.query

                _stateFlow.update {
                    it.copy(query = query)
                }

                viewModelScope.launch {
                    // Clears old results when the query becomes too short so the UI does not
                    // keep showing suggestions that no longer match the user's input
                    if (query.length < 3) {
                        _stateFlow.update {
                            it.copy(
                                results = emptyList(),
                                selectedMovie = null,
                                loading = false
                            )
                        }
                    } else if (query.length % 3 == 0) {
                        // Limited search to every 3 characters to reduce unnecessary API requests
                        _stateFlow.update { it.copy(loading = true) }

                        try {
                            val results = repo.searchMovies(query)
                            _stateFlow.update {
                                it.copy(
                                    results = results,
                                    loading = false
                                )
                            }
                        } catch (e: Exception) {
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
                // Keeps the selected movie in state
                _stateFlow.update {
                    it.copy(selectedMovie = intent.movie)
                }
            }

            MovieSearchIntent.SaveSelectedMovie -> {
                saveSelectedMovie()
            }
        }
    }

    fun undoSaveMovie(movie: Movie) {
        viewModelScope.launch {
            try {
                // The undo action removes the movie that was just saved
                repo.deleteMovie(movie)
                Log.d(LOG_TAG, "Undo save for movie: ${movie.title}")
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Failed to undo saved movie", e)
            }
        }
    }

    private fun saveSelectedMovie() {
        val selected = stateFlow.value.selectedMovie ?: return

        viewModelScope.launch {
            try {
                // Prevents duplicate database entries for the same movie
                if (repo.alreadyExists(selected.imdbId)) {
                    Log.d(LOG_TAG, "Movie already exists: ${selected.imdbId}")
                    _effectFlow.emit(MovieSearchEffect.MovieAlreadyExists(selected.title))
                    return@launch
                }

                // Converts the search result model into the locally stored Movie model before inserting it into the Room database
                val movie = Movie(
                    imdbId = selected.imdbId,
                    title = selected.title,
                    description = selected.description,
                    year = selected.year,
                    imageUrl = selected.imageUrl,
                    averageRating = selected.averageRating,
                    genres = selected.genres
                )

                repo.addMovie(movie)

                Log.d(LOG_TAG, "Movie saved: ${selected.title}")
                _effectFlow.emit(MovieSearchEffect.SaveSucceeded(movie))
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Failed to save selected movie", e)
            }
        }
    }
}
