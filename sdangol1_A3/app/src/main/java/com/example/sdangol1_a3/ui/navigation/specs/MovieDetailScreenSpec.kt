package com.example.sdangol1_a3.ui.navigation.specs

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.sdangol1_a3.R
import com.example.sdangol1_a3.ui.common.LoadingContent
import com.example.sdangol1_a3.ui.details.MovieDetailScreen
import com.example.sdangol1_a3.ui.viewmodel.MovieDetailViewModel
import com.example.sdangol1_a3.ui.viewmodel.MovieViewModelFactory
import com.example.sdangol1_a3.ui.viewmodel.intent.MovieDetailIntent

data object MovieDetailScreenSpec : IScreenSpec {
    private const val ROUTE_BASE = "detail"
    private const val ARG_MOVIE_ID = "movieId"

    private fun buildFullRoute(argVal: String): String {
        return if (argVal == ARG_MOVIE_ID) {
            "$ROUTE_BASE/{$argVal}"
        } else {
            "$ROUTE_BASE/$argVal"
        }
    }

    override val route = buildFullRoute(ARG_MOVIE_ID)
    override val title: Int = R.string.app_name

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ARG_MOVIE_ID) {
            type = NavType.StringType
        }
    )

    override fun buildRoute(vararg args: String?) = buildFullRoute(args[0] ?: ARG_MOVIE_ID)

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
        )[MovieDetailViewModel::class]

        val (state, dispatcher, effects) = viewModel.use(navBackStackEntry)

        val movie = state.movie
        if (movie == null) {
            LoadingContent(modifier = modifier)
        } else {
            MovieDetailScreen(
                movie = movie,
                cast = state.cast,
                modifier = modifier,
                castButtonEnabled = true,
                onLoadCast = {
                    dispatcher.invoke(MovieDetailIntent.FetchCast(movie.imdbId))
                },
                onViewPerson = {
                    // TODO launch IMDb intent
                }
            )
        }
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {
        Text("")
    }
}
