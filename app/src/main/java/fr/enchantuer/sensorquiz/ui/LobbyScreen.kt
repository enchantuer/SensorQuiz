package fr.enchantuer.sensorquiz.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.enchantuer.sensorquiz.R
import fr.enchantuer.sensorquiz.ui.theme.SensorQuizTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import fr.enchantuer.sensorquiz.ui.theme.LavenderPurple
import fr.enchantuer.sensorquiz.ui.theme.violetGradientBackground

@Composable
fun LobbyScreen(
    onStartClick: () -> Unit,
    onThemeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .violetGradientBackground()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CodeCard(code = "1234")

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Settings(onThemeClick = onThemeClick)
            PlayerList(playerList = listOf("Joueur 1", "Joueur 2", "Joueur 3"))
            Button(
                onClick = onStartClick,
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(4.dp),
            ) {
                Text(
                    text = stringResource(R.string.start),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun PlayerList(
    playerList: List<String>,
    modifier: Modifier = Modifier,
) {
    SettingCard(
        clickable = false,
        onClick = {},
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .padding(vertical = 4.dp),
        ) {
            itemsIndexed(playerList) { index, player ->
                Row {
                    Text(
                        text = player,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp, vertical = 4.dp)
                    )
                }
                if (index < playerList.size - 1) { // Évite d'ajouter un divider après la dernière ligne
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.LightGray
                    )
                }
            }
        }
    }
}

@Composable
fun Settings(
    onThemeClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Button(
            onClick = onThemeClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = ButtonDefaults.buttonElevation(4.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
            ),
            contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 16.dp
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.choose_theme),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = "Catégorie 1",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        SettingItem(
            initialChecked = false,
            text = R.string.choose_localisation,
        )
    }
}

@Composable
fun CodeCard(
    code: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
    ) {
       Text(
           text = code.uppercase(),
           style = MaterialTheme.typography.headlineMedium,
           modifier = Modifier
               .padding(16.dp)
       )
    }
}

@Preview(showBackground = true)
@Composable
fun LobbyScreenPreview() {
    SensorQuizTheme {
        LobbyScreen(onStartClick = {}, onThemeClick = {})
    }
}