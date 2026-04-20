package com.example.sdangol1_a3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.sdangol1_a3.ui.navigation.specs.IScreenSpec

@Composable
fun MovieNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = IScreenSpec.ROOT
    ) {
        navigation(
            route = IScreenSpec.ROOT,
            startDestination = IScreenSpec.startDestination
        ) {
            IScreenSpec.allScreens.forEach { (_, screen) ->
                if(screen != null) {
                    composable(
                        route = screen.route,
                        arguments = screen.arguments
                    ) { navBackStackEntry ->
                        screen.Content(
                            modifier = Modifier,
                            navController = navController,
                            navBackStackEntry = navBackStackEntry
                        )
                    }
                }
            }
        }
    }
}
