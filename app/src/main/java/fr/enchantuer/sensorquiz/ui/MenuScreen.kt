package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R

@Composable
fun MenuScreen(
    canResume: Boolean,
    modifier: Modifier = Modifier
) {
    val menuOptions = listOfNotNull(
        if (canResume) stringResource(R.string.resume) else null,
        stringResource(R.string.solo),
        stringResource(R.string.multiplayer),
        stringResource(R.string.setting),
    )

    Column(
        modifier = modifier.padding(horizontal = 16.dp).fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        menuOptions.forEach { menuOption ->
            Button(
                onClick = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
            ) {
                Text(text = menuOption)
            }
        }
    }
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