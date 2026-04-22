package com.example.sdangol1_a3.ui.navigation.specs

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

sealed interface IScreenSpec {
    companion object {
        val allScreens = IScreenSpec::class.sealedSubclasses.associate {
            it.objectInstance?.route to it.objectInstance
        }

        const val ROOT = "movies"
        val startDestination = MovieListScreenSpec.route

        @Composable
        fun TopBar(
            navController: NavHostController,
            navBackStackEntry: NavBackStackEntry?
        ) {
            val route = navBackStackEntry?.destination?.route ?: ""
            allScreens[route]?.TopAppBarContent(
                navController = navController,
                navBackStackEntry = navBackStackEntry
            )
        }
    }

    val route: String
    val title: Int
    val arguments: List<NamedNavArgument>

    fun buildRoute(vararg args: String?): String

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun TopAppBarContent(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    ) {
        TopAppBar(
            navigationIcon = if (navController.previousBackStackEntry != null) {
                {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            } else {
                { }
            },
            title = {
                Text("Android Movie Database")
            },
            actions = {
                TopAppBarActions(
                    navController = navController,
                    navBackStackEntry = navBackStackEntry
                )
            }
        )
    }

    @Composable
    fun Content(
        modifier: Modifier,
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry
    )

    @Composable
    fun TopAppBarActions(
        navController: NavHostController,
        navBackStackEntry: NavBackStackEntry?
    )
}
