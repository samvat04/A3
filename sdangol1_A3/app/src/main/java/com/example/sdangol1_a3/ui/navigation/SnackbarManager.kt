package com.example.sdangol1_a3.ui.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

data class SnackbarMessage(
    val message: String,
    val actionLabel: String? = null
)

object SnackbarManager {
    // SharedFlow is used so snackbar events can be emitted from ViewModels or screen specs
    private val _messages = MutableSharedFlow<SnackbarMessage>()
    val messages = _messages.asSharedFlow()

    suspend fun showMessage(
        message: String,
        actionLabel: String? = null
    ) {
        _messages.emit(SnackbarMessage(message, actionLabel))
    }
}
