package com.example.sdangol1_a3.ui.navigation.specs

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.sdangol1_a3.R
import com.example.sdangol1_a3.ui.search.MovieSearchScreen
import com.example.sdangol1_a3.ui.viewmodel.MovieSearchViewModel
import com.example.sdangol1_a3.ui.viewmodel.MovieViewModelFactory
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

        val viewModel = ViewModelProvider(
            store = navBackStackEntry.viewModelStore,
            factory = MovieViewModelFactory(),
            defaultCreationExtras = MovieViewModelFactory.creationExtras(
                navBackStackEntry.defaultViewModelCreationExtras,
                context
            )
        )[MovieSearchViewModel::class]

        val (state, dispatcher, effects) = viewModel.use(navBackStackEntry)

        MovieSearchScreen(
            query = state.query,
            results = state.results,
            selectedMovie = state.selectedMovie,
            modifier = modifier,
            searchEnabled = state.query.isNotBlank(),
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
