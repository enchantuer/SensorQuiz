package fr.enchantuer.sensorquiz.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R

@Composable
fun LocalisationScreen(
    onNextButtonClick: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.use_localisation)
        )
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            LocalisationButton(text = R.string.with, onClick = { onNextButtonClick(true) })
            LocalisationButton(text = R.string.without, onClick = { onNextButtonClick(false) })
        }
    }
}

@Composable
fun LocalisationButton(
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
    ) {
        Text(text = stringResource(text))
    }
}

@Preview(showBackground = true)
@Composable
fun LocalisationScreenPreview() {
    LocalisationScreen(onNextButtonClick = {})
}