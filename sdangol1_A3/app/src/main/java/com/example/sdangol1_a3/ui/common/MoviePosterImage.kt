package com.example.sdangol1_a3.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun MoviePosterImage(
    imageUrl: String?,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    if(imageUrl.isNullOrBlank()) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No Image Available")
        }
    } else {
        AsyncImage(
            model = imageUrl,
            contentDescription = contentDescription,
            modifier = modifier
                .fillMaxWidth()
                .height(220.dp)
        )
    }
}
