package fr.enchantuer.sensorquiz.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HorizontalButtonsList(
    modifier: Modifier = Modifier,
    list: List<Pair<String, () -> Unit>>) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        list.forEach { item ->
            Button(
                onClick = item.second,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Text(text = item.first)
            }
        }
    }
}