package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.ui.components.HorizontalButtonsList

@Composable
fun MenuScreen(
    canResume: Boolean,
    modifier: Modifier = Modifier
) {
    HorizontalButtonsList(
        modifier = modifier
            .padding(horizontal = 32.dp)
            .fillMaxSize(),
        list = listOfNotNull(
            if (canResume) Pair(stringResource(R.string.resume), {}) else null,
            Pair(stringResource(R.string.solo), {}),
            Pair(stringResource(R.string.multiplayer), {}),
            Pair(stringResource(R.string.setting), {}),
        )
    )
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen(canResume = false, modifier = Modifier.fillMaxHeight())
}

@Preview
@Composable
fun MenuScreenResumePreview() {
    MenuScreen(canResume = true, modifier = Modifier.fillMaxHeight())
}