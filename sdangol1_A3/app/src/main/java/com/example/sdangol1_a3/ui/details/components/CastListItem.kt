package com.example.sdangol1_a3.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sdangol1_a3.data.CastMember
import com.example.sdangol1_a3.ui.common.MovieButton
import com.example.sdangol1_a3.ui.common.ValueText

@Composable
fun CastListItem(
    castMember: CastMember,
    modifier: Modifier = Modifier,
    onViewPerson: (CastMember) -> Unit = {}
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                ValueText(text = castMember.name)
                ValueText(text = castMember.job)
                ValueText(text = castMember.characters.joinToString())
            }

            MovieButton(
                text = "IMDb",
                onClick = { onViewPerson(castMember) }
            )
        }
    }
}
