package com.example.sdangol1_a3.ui.navigation.specs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.sdangol1_a3.R
import com.example.sdangol1_a3.ui.list.MovieListScreen
import com.example.sdangol1_a3.ui.viewmodel.MovieListViewModel
import com.example.sdangol1_a3.ui.viewmodel.MovieViewModelFactory

data object MovieListScreenSpec : IScreenSpec {
    override val route = "list"
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
        )[MovieListViewModel::class]

        val (state, dispatcher, effects) = viewModel.use(navBackStackEntry)

        MovieListScreen(
            movies = state.movies,
            modifier = modifier,
            onSelectMovie = { movie ->
                navController.navigate(
                    MovieDetailScreenSpec.buildRoute(movie.id.toString())
                )
            },
            onToggleFavorite = {
                // TODO dispatcher.invoke(...)
            }
        )
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {
        IconButton(
            onClick = { navController.navigate(MovieSearchScreenSpec.route) }
        ) {
            Icon(
                imageVector = Icons.Filled.Add,
                contentDescription = "Search"
            )
        }
    }
}
