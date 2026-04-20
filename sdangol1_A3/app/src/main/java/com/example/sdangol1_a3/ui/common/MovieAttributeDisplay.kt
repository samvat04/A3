package com.example.sdangol1_a3.ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun MovieAttributeDisplay(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        LabelText(text = label)
        ValueText(text = value)
    }
}
