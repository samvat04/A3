package com.example.sdangol1_a3.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.sdangol1_a3.ui.navigation.specs.IScreenSpec

@Composable
fun MovieTopBar(
    navController: NavHostController
) {
    val navBackStackEntryState = navController.currentBackStackEntryAsState()

    IScreenSpec.TopBar(
        navController = navController,
        navBackStackEntry = navBackStackEntryState.value
    )
}
