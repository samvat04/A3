package com.example.sdangol1_a3.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.data.MovieRepo
import com.example.sdangol1_a3.ui.viewmodel.effect.MovieListEffect
import com.example.sdangol1_a3.ui.viewmodel.intent.MovieListIntent
import com.example.sdangol1_a3.ui.viewmodel.state.MovieListState
import com.example.sdangol1_a3.util.MovieFilterOption
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieListViewModel(
    private val repo: MovieRepo,
    savedStateHandle: SavedStateHandle
) : ViewModel(), IViewModelContract<MovieListState, MovieListIntent, MovieListEffect> {

    private val _stateFlow = MutableStateFlow(MovieListState())
    override val stateFlow = _stateFlow.asStateFlow()

    private val _effectFlow = MutableSharedFlow<MovieListEffect?>()
    override val effectFlow = _effectFlow.asSharedFlow()

    private var allMovies: List<Movie> = emptyList()

    init {
        viewModelScope.launch {
            repo.getMovies().collectLatest { movies ->
                allMovies = movies
                applyFilter()
            }
        }
    }

    override fun handleIntent(intent: MovieListIntent) {
        when (intent) {
            is MovieListIntent.ToggleFavorite -> toggleFavorite(intent.movie)
            is MovieListIntent.SetFilter -> {
                _stateFlow.update { it.copy(filter = intent.filter) }
                applyFilter()
            }
        }
    }

    private fun applyFilter() {
        val filter = _stateFlow.value.filter

        val filteredMovies = when (filter) {
            MovieFilterOption.ALL -> allMovies
            MovieFilterOption.FAVORITES -> allMovies.filter { it.isFavorite }
        }

        _stateFlow.update {
            it.copy(
                movies = filteredMovies,
                loading = false
            )
        }
    }

    private fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            repo.updateMovie(movie.copy(isFavorite = !movie.isFavorite))
        }
    }
}
