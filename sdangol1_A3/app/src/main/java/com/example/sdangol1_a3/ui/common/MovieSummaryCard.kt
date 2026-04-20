package com.example.sdangol1_a3.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MovieSummaryCard(
    title: String,
    description: String,
    year: String,
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = modifier.then(
            if(onClick != null) Modifier.clickable { onClick() } else Modifier
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            HeaderText(text = title)
            ValueText(text = year)
            ValueText(text = description)
            trailingContent?.invoke()
        }
    }
}
