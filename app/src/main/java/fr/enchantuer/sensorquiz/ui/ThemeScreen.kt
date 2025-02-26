package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.ui.components.HorizontalButtonsList
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme


@Composable
fun ThemeScreen(
    onNextButtonClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    HorizontalButtonsList(
        modifier = modifier
            .padding(horizontal = 32.dp),
        list = listOf(
            Pair("Catégorie 1") { onNextButtonClick("Catégorie 1") },
            Pair("Catégorie 2") { onNextButtonClick("Catégorie 2") },
            Pair("Catégorie 3") { onNextButtonClick("Catégorie 3") },
        )
    )
}

@Preview
@Composable
fun ThemeScreenPreview() {
    SensorQuizTheme {
        ThemeScreen(modifier = Modifier.fillMaxHeight(), onNextButtonClick = {})
    }
}