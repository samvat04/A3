package com.example.sdangol1_a3.ui.navigation.specs

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.sdangol1_a3.ui.details.components.DeleteMovieConfirmationDialog
import com.example.sdangol1_a3.ui.navigation.SnackbarManager
import com.example.sdangol1_a3.ui.viewmodel.MovieDetailViewModel
import com.example.sdangol1_a3.ui.viewmodel.MovieViewModelFactory
import com.example.sdangol1_a3.ui.viewmodel.collectInLaunchedEffect
import com.example.sdangol1_a3.ui.viewmodel.effect.MovieDetailEffect
import com.example.sdangol1_a3.ui.viewmodel.intent.MovieDetailIntent
import com.example.sdangol1_a3.util.ImdbIntentFactory
import java.util.UUID

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

        val movieIdArg = navBackStackEntry.arguments?.getString(ARG_MOVIE_ID)

        LaunchedEffect(movieIdArg) {
            // Loads the selected movie when the detail screen is entered using the
            // movie ID passed through navigation arguments
            movieIdArg?.let {
                dispatcher.invoke(MovieDetailIntent.LoadMovie(UUID.fromString(it)))
            }
        }

        effects.collectInLaunchedEffect { effect ->
            when (effect) {
                is MovieDetailEffect.DeleteSucceeded -> {
                    SnackbarManager.showMessage("${effect.movieTitle} deleted!")
                    navController.navigateUp()
                }
                MovieDetailEffect.DeleteFailed -> {
                    SnackbarManager.showMessage("Failed to delete movie")
                }
                null -> {}
            }
        }

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
                onViewPerson = { castMember ->
                    // Opening the IMDb page is given to the intent factory
                    context.startActivity(
                        ImdbIntentFactory.buildPersonPageIntent(castMember)
                    )
                }
            )
        }
    }

    @Composable
    override fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {
        if (navBackStackEntry == null) return

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

        var showDeleteDialog by remember { mutableStateOf(false) }

        val movie = state.movie ?: return

        Row {
            IconButton(
                onClick = {
                    dispatcher.invoke(MovieDetailIntent.ToggleFavorite(movie))
                }
            ) {
                Icon(
                    imageVector = if (movie.isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = if (movie.isFavorite) {
                        "Unfavorite movie"
                    } else {
                        "Favorite movie"
                    }
                )
            }

            IconButton(
                onClick = {
                    showDeleteDialog = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete movie"
                )
            }
        }

        if (showDeleteDialog) {
            // The delete confirmation
            DeleteMovieConfirmationDialog(
                movie = movie,
                onDismissRequest = {
                    showDeleteDialog = false
                },
                onConfirmation = {
                    showDeleteDialog = false
                    dispatcher.invoke(MovieDetailIntent.DeleteMovie(movie))
                }
            )
        }
    }
}
