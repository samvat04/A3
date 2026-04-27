package com.example.sdangol1_a3.ui.details.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.sdangol1_a3.data.Movie
import com.example.sdangol1_a3.ui.common.LabelText
import com.example.sdangol1_a3.ui.common.ValueText

@Composable
fun DeleteMovieConfirmationDialog(
    movie: Movie,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = true
        )
    ) {
        Card {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Delete confirmation
                LabelText(text = "Confirm Delete")
                ValueText(
                    modifier = Modifier.padding(top = 8.dp),
                    text = "Are you sure you wish to delete this movie?\n\n${movie.title}"
                )
                Row {
                    TextButton(onClick = onDismissRequest) {
                        LabelText(text = "Cancel")
                    }
                    TextButton(onClick = onConfirmation) {
                        LabelText(text = "Delete")
                    }
                }
            }
        }
    }
}
