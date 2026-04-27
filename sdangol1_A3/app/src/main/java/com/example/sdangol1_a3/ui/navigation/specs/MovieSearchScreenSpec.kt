package com.example.sdangol1_a3.ui.navigation.specs

import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.sdangol1_a3.MainActivity
import com.example.sdangol1_a3.R
import com.example.sdangol1_a3.ui.navigation.SnackbarManager
import com.example.sdangol1_a3.ui.search.MovieSearchScreen
import com.example.sdangol1_a3.ui.viewmodel.MovieSearchViewModel
import com.example.sdangol1_a3.ui.viewmodel.MovieViewModelFactory
import com.example.sdangol1_a3.ui.viewmodel.collectInLaunchedEffect
import com.example.sdangol1_a3.ui.viewmodel.effect.MovieSearchEffect
import com.example.sdangol1_a3.ui.viewmodel.intent.MovieSearchIntent

data object MovieSearchScreenSpec : IScreenSpec {
    override val route = "search"
    override val title: Int = R.string.app_name
    override val arguments: List<NamedNavArgument> = emptyList()

    override fun buildRoute(vararg args: String?) = route

    @Composable
    override fun Content(
        modifier: Modifier,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry
    ) {
        val context = LocalContext.current

        // The ViewModel is scoped to this back stack entry while the search screen remains on the navigation stack
        val viewModel = ViewModelProvider(
            store = navBackStackEntry.viewModelStore,
            factory = MovieViewModelFactory(),
            defaultCreationExtras = MovieViewModelFactory.creationExtras(
                navBackStackEntry.defaultViewModelCreationExtras,
                context
            )
        )[MovieSearchViewModel::class]

        val (state, dispatcher, effects) = viewModel.use(navBackStackEntry)

        effects.collectInLaunchedEffect { effect ->
            when (effect) {
                is MovieSearchEffect.SaveSucceeded -> {
                    // Save success is handled with an undo snackbar
                    val result = MainActivity.appSnackbarHostState.showSnackbar(
                        message = "${effect.movie.title} saved!",
                        actionLabel = "Undo",
                        duration = androidx.compose.material3.SnackbarDuration.Short
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.undoSaveMovie(effect.movie)
                    }
                }
                is MovieSearchEffect.MovieAlreadyExists -> {
                    SnackbarManager.showMessage("${effect.title} already in database.")
                }
                MovieSearchEffect.SearchFailed -> {
                    SnackbarManager.showMessage("Search failed")
                }
                null -> {}
            }
        }

        MovieSearchScreen(
            query = state.query,
            results = state.results,
            selectedMovie = state.selectedMovie,
            modifier = modifier,
            searchEnabled = state.query.length >= 3,
            saveEnabled = state.selectedMovie != null,
            onQueryChange = { query ->
                dispatcher.invoke(MovieSearchIntent.UpdateQuery(query))
            },
            onSearch = {
                dispatcher.invoke(MovieSearchIntent.Search(state.query))
            },
            onSelectMovie = { movie ->
                dispatcher.invoke(MovieSearchIntent.SelectMovie(movie))
            },
            onSaveMovie = {
                dispatcher.invoke(MovieSearchIntent.SaveSelectedMovie)
            }
        )
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) { }
}
