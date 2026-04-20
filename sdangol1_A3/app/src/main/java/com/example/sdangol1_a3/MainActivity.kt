package com.example.sdangol1_a3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.sdangol1_a3.ui.navigation.MovieNavHost
import com.example.sdangol1_a3.ui.navigation.MovieTopBar
import com.example.sdangol1_a3.ui.navigation.SnackbarManager
import com.example.sdangol1_a3.ui.theme.Sdangol1_A3Theme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Sdangol1_A3Theme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(Unit) {
                    SnackbarManager.messages.collectLatest { message ->
                        snackbarHostState.showSnackbar(message)
                    }
                }

                Scaffold(
                    topBar = { MovieTopBar(navController = navController) },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { innerPadding ->
                    MovieNavHost(
                        navController = navController,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
