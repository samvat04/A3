package com.example.sdangol1_a3.ui.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sdangol1_a3.data.CastMember

@Composable
fun CastList(
    cast: List<CastMember>,
    modifier: Modifier = Modifier,
    onViewPerson: (CastMember) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        cast.forEach { member ->
            CastListItem(
                castMember = member,
                onViewPerson = onViewPerson
            )
        }
    }
}
