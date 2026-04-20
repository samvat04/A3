package com.example.sdangol1_a3.ui.common

import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FavoriteToggleButton(
    isFavorite: Boolean,
    onToggle: () -> Unit
) {
    IconButton(onClick = onToggle) {
        Text(if(isFavorite) "★" else "☆")
    }
}
